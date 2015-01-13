# Computes the Gibbs free energy of all given states from a Markov
# transition matrix of probabilities between said states
# Also computes associated error and the most likely folding path
# Scott Gigante, scottgigante@gmail.com, Nov 2014

library(matrixStats)
library(gtools)
library(ggplot2)

## Constants
DEFAULT_TEMP = 300 # temperature if none is given
DEFAULT_FILENAME = "select_above_delete_yes_05.dat"
DEFAULT_ALPHA_SCALE = 10 # number of possible alpha values
TOLERANCE = 0.01 # tolerance for error
K = 0.001987204118 # Boltzmann constant in kcal/mol/K
DAT_PREFIX = "../dat/"
PDF_PREFIX = "../pdf/"
OUTPUT_PREFIX = "free_energy_" # to be prepended to input file name for output
PATH_PREFIX = "path_"
PATH_SUFFIX = "_path.dat" # to be appended to path file

## Define functions

# Use first-order Taylor polynomials to calculate a distribution of the first eigenvector
# Note: n, q, w, T not required, but already calculated so may as well
# w=rowSums(u), w[sapply(w, function(row) sum(row)==0)]=1
# u is a count matrix of transitions from state i to state j
# Returns a vector of the standard deviation of each value in the first eigenvector of T
# Note - some (~1-2%) values return between 4-30 times too large - this method redundant for now
CalculateStandardDeviation = function(u,w,T=u/w,n=nrow(T),q=eigen(T)$vectors[,1]) {
  ## Find error values for free energy
  phi = solve(diag(1,n)-T)  
  s = array(0,dim=c(n,n,n))
  for (i in 1:n) {
    s[i,,]=matrix(q,nrow=length(q),ncol=1)%*%matrix(phi[,i],nrow=1,ncol=ncol(phi))
  }
  var = rep(0,n)
  for (i in 1:n) {
    for (j in 1:n) {
      var[i] = var[i]+(1/(w[j]*w[j]*(w[j]+1)))*t(s[i,j,])%*%(w[j]*diag(u[j,])-u[j,]%*%t(u[j,]))%*%s[i,j,]
    }
  }
  var=Re(var)
  return(sqrt(var))
}

# Use dirichlet sampling to calculate a distribution of the first eigenvector
# Note: n not required, but already calculated so may as well
# u is a transition count matrix of transitions from state i to state j
# samples is the number of times to sample the distribution
# Returns a vector of the standard deviation of each value in the first eigenvector of T
SampleStandardDeviation = function(u,n=nrow(u),samples=1000) {
  
  TSample = array(0,dim=c(n,n,samples))
  qSample = matrix(0,nrow=n,ncol=samples)

  # Sample each element in T samples times
  for (i in 1:n) {
    if (sum(u[i,] != 0)) {
      TSample[i,,]=t(rdirichlet(samples,u[i,]))
    }
  }

  # calculate eigenvectors
  for (k in 1:samples) {
    qSample[,k]=abs(Re(eigen(t(TSample[,,k]))$vectors[,1]))
  }
  
  # Finally, find standard deviation of each element in this collection of eigenvectors
  return(rowSds(qSample))
}

# Reads transition data from a file
# Prints free energy and error values in text and plot form
ComputeFreeEnergy = function(filename,t,alpha_scale,sample=TRUE) {
  A = read.table(paste0(DAT_PREFIX,filename))  
  
  ## For especially large data sets, better to remove all zero data first
  non_zero = apply(A, 1, function(row) row[3] !=0 )
  A=A[non_zero,]
  R = sort(unique(c(A[,1],A[,2]))) # record row numbers
  
  ## Create count matrix including all possible states in data set
  n = length(R)
  Z = matrix(0,nrow=n,ncol=n)
  rownames(Z)=R
  colnames(Z)=R
  for (i in 1:nrow(A)) {
    Z[match(A[i,1],R),match(A[i,2],R)]=A[i,3]
  }
  
  ## Remove empty rows
  non_zero = apply(Z,1,function(row) sum(row)!=0) | apply(Z,2,function(col) sum(col)!=0)
  Z = Z[non_zero,non_zero]
  R = R[non_zero]	
  n=nrow(Z)
  
  # Generate Dirichlet parameters and associated expected probability matrix
  alpha = matrix(1,n,n) # using a uniform prior
  alpha[apply(Z, 1, function(row) sum(row)==0)] = 0
  u = alpha+Z
  w = rowSums(u)
  w[sapply(w, function(row) sum(row)==0)] = 1
  T = u/w
  
  ## Find eigenthings. The largest eigenvalue of T must be 1
  B = eigen(t(T))
  lambda = B$values[1]
  # eigenvalues are in descending order; the first one is steady state
  if (abs(lambda-1) > TOLERANCE) {
    message(sprintf("Note: eigenvalue of q == %f != 1",lambda))
  }
  q=B$vectors[,1] 
  stopifnot(Re(q)==q) # probabilities should be real
  q = abs(Re(q))
  
  ## Create the free energy vector
  G = -K*t*log(q/max(q))
  R = R[is.finite(G)]
  G = G[is.finite(G)]

  q_sd = if (sample) SampleStandardDeviation(u,n, samples=100) else CalculateStandardDeviation(u,w,T,n,q)
  G_sd = K*t*sqrt((q_sd/q)^2+(q_sd[which.max(q)]/max(q))^2)
  
  ## Convert to meaningful format. Cluster 235 refers to d=23, a=0.5
  d = floor(R/alpha_scale)
  a = R%%alpha_scale
  z_energy = matrix(Inf,nrow=max(d),ncol=alpha_scale) # free energy infinite where inaccessible
  z_error = matrix(Inf,nrow=max(d),ncol=alpha_scale) 
  x = 1:max(d)
  y = (0:(alpha_scale-1))/alpha_scale
  O_energy = matrix("",nrow=max(d),ncol=alpha_scale)
  O_error = matrix("",nrow=max(d),ncol=alpha_scale)
  for (i in 1:length(G)) {
    z_energy[d[i],a[i]+1]=G[i]
    z_error[d[i],a[i]+1]=G_sd[i]
    O_energy[d[i],a[i]+1]=sprintf("%f",G[i])
    O_error[d[i],a[i]+1]=sprintf("%f ± %f",G[i],G_sd[i])
  }
  
  ## Print to a data file
  rownames(O_energy) = x
  colnames(O_energy) = y
  write.table(t(O_energy),sprintf("%s%s%s",DAT_PREFIX,OUTPUT_PREFIX,filename),sep="\t",col.names=NA)
  
  rownames(O_error) = x
  colnames(O_error) = y
  write.table(t(O_error),sprintf("%s%serror_%s",DAT_PREFIX,OUTPUT_PREFIX,filename),sep="\t",col.names=NA)
  
  ## Run Java code to find shortest path
  system(sprintf("java -jar ../MinimumFreeEnergyPath.jar %s%s%s %f %f",DAT_PREFIX,OUTPUT_PREFIX,filename,x[length(x)],y[1]))
  # Plot the shortest path line graph
  path = read.table(paste0(DAT_PREFIX,OUTPUT_PREFIX,filename,PATH_SUFFIX))
  path_x = c(0:(nrow(path)-1))
  path_y = path[,3]
  path_sd = G_sd[match(alpha_scale*(path[,1]+path[,2]),R)]
  
  df = data.frame(path_x,path_y,path_sd)
  plot = ggplot(df, aes(x=path_x, y=path_y)) + geom_line() + geom_errorbar(data = df, aes(x=path_x, y=path_y, ymin = path_y-path_sd, ymax = path_y+path_sd), colour = 'red', width=0.4) + xlab("Path Length (bins)") + ylab("Gibbs Free Energy (kcal/mol)") + ggtitle(paste0("Most Probable Folding Path\n\n",filename))
  ggsave(sprintf("%s%s%s%s.pdf",PDF_PREFIX,OUTPUT_PREFIX,PATH_PREFIX,filename), plot)
  
  pdf(sprintf("%s%s%s.pdf",PDF_PREFIX,OUTPUT_PREFIX,filename))
  filled.contour(x,y,z_energy,main="Free Energy (kcal/mol)", sub=filename,xlab="End-to-End Distance (Å)",ylab="Alpha Helicity", zlim=c(0,10), color.palette=colorRampPalette(c("red","yellow","green","cyan","blue","purple")), nlevels = 100, plot.axes={lines(path[,1],path[,2],type="l",col="purple",lty=1,lwd=3); axis(1); axis(2)})
  dev.off()

  pdf(sprintf("%s%serror_%s.pdf",PDF_PREFIX,OUTPUT_PREFIX, filename))
  filled.contour(x,y,z_error,main="Free Energy Error (kcal/mol)", sub=filename,xlab="End-to-End Distance (Å)",ylab="Alpha Helicity", zlim=c(0,1), color.palette=colorRampPalette(c("red","yellow","green","cyan","blue","purple")), nlevels = 100)
  dev.off()
}

# Check the transition matrix for detailed balance
# Test function. Returns a matrix of booleans for each value of T
checkDetailedBalance = function(T) {
  n = nrow(T)
  success = matrix(TRUE, nrow=n, ncol=n)
  q = eigen(T)$vectors[,1]
  for (i in 1:n) {
    for (j in i:n) {
      if (q[i]*T[i,j] != q[j]*T[j,i]) {
        success[i,j] = FALSE
        success[j,i] = FALSE
      }
    }
  }
  return(success)
}  

## Main function
# Read in command line arguments
args = commandArgs(trailingOnly=TRUE)
if (length(args) < 1) {
  message("Usage: RScript ComputeFreeEnergy.r <inputfiles> <temperature>")
  message(paste0("Using default filename: ", DEFAULT_FILENAME))
  args=c(DEFAULT_FILENAME)
}
if (suppressWarnings(is.na(as.numeric(args[1])))) {
  message("Usage: RScript ComputeFreeEnergy.r <inputfiles> <temperature>")
  message(paste0("Using default temperature: ", DEFAULT_TEMP))
  args = c(DEFAULT_TEMP, args)
}
if (suppressWarnings(is.na(as.numeric(args[2])))) {
  message("Usage: RScript ComputeFreeEnergy.r <inputfiles> <temperature>")
  message(paste0("Using default alpha scale: ", DEFAULT_ALPHA_SCALE))
  args = c(DEFAULT_ALPHA_SCALE, args)
}
t = as.numeric(args[1])
alpha_scale = as.numeric(args[2])

# Run!
message(sprintf("Temperature = %d K",t))
message(sprintf("Alpha Scale = %d",alpha_scale))
for (i in 3:(length(args))) {
  message(paste0(args[i],": Calculating free energies"))
  message(sprintf("%s: Complete in %f s", args[i], system.time(ComputeFreeEnergy(args[i], t, alpha_scale))[1]))
  #message(paste0(args[i],": Calculating free energies without sampling"))
  #message(sprintf("%s: Complete in %f s", args[i], system.time(ComputeFreeEnergy(args[i],FALSE))[1]))
}
