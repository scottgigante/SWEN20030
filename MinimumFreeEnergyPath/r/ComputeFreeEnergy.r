# Computes the Gibbs free energy of all given states from a Markov
# transition matrix of probabilities between said states
# Scott Gigante, gigantes@student.unimelb.edu.au, Nov 2014

## Constants
DEFAULT_TEMP = 300 # temperature if none is given
DEFAULT_FILENAME = "select_above_delete_yes_05.dat"
R_OFFSET = 1 # rows in input given from 0, r counts from 1 (wut)
TOLERANCE = 0.01 # tolerance for error
K = 0.001987204118 # Boltzmann constant in kcal/mol/K
OUTPUT_PREFIX = "free_energy" # to be prepended to input file name for output
DEBUG = TRUE # toggles debug functions
ALPHA_SCALE = 10 # number of possible alpha values

## Read in command line arguments
args = commandArgs(trailingOnly=TRUE)
if (length(args) < 1) {
	print("Usage: RScript ComputeFreeEnergy.r <inputfiles> <temperature>")
	print(paste("Using default filename: ", DEFAULT_FILENAME, sep=""))
  args=c(DEFAULT_FILENAME)
}
if (suppressWarnings(is.na(as.numeric(args[length(args)])))) {
  print("Usage: RScript ComputeFreeEnergy.r <inputfiles> <temperature>")
  print(paste("Using default temperature: ", DEFAULT_TEMP, sep=""))
  args = c(args, DEFAULT_TEMP)
}
t = as.numeric(args[length(args)])

for (i in 1:(length(args)-1)) {
  filename = args[i]
  A = read.table(filename)
  
  ## For especially large data sets, better to remove all zero data first
  non_zero = apply(A, 1, function(row) row[3] !=0 )
  A=A[non_zero,]
  R = sort(unique(c(A[,1],A[,2]))) # record row numbers
  
  ## Create transition matrix including all possible states, even those not included in data set
  n = length(R)
  T = matrix(0,nrow=n,ncol=n)
  rownames(T)=R
  colnames(T)=R
  for (i in 1:nrow(A)) {
    T[match(A[i,1],R),match(A[i,2],R)]=A[i,3]
  }
  
  ## Remove empty rows to create stochastic matrix
  i=1
  while (i <= nrow(T)) {
    if (sum(T[i,]) == 0 && sum(T[,i]) == 0) { 
      # row sums to zero, unattainable state - remove it
      T = T[-i,-i]
      R = R[-i]
    } else {
      # this state is good, move on
      i=i+1
    }
  }
  
  ## Find eigenthings. The largest eigenvalue of T must be 1
  B = eigen(T)
  # eigenvalues are in descending order; the first one is steady state
  if (abs(B$values[1]-1) > TOLERANCE) {
    print(paste("Note: eigenvalue of q == ",B$values[1]," != 1",sep=""))
  }
  q=B$vectors[,1] 
  stopifnot(Re(q)==q) # probabilities should be real
  q = Re(q)
  
  ## Create the free energy vector
  G = -K*t*log(q/max(q))
  R = R[is.finite(G)]
  G = G[is.finite(G)]
  
  ## Convert to meaningful format. Cluster 235 refers to d=23, a=0.5
  d = floor(R/ALPHA_SCALE)
  a = R%%ALPHA_SCALE
  z = matrix(Inf,nrow=max(d),ncol=ALPHA_SCALE) # free energy infinite where inaccessible
  x = 1:max(d)
  y = (0:(ALPHA_SCALE-1))/ALPHA_SCALE
  for (i in 1:length(G)) {
    z[d[i],a[i]]=G[i]
  }
  
  ## Print to a data file
  filename = paste(OUTPUT_PREFIX, filename, sep="_")
  rownames(z) = x
  colnames(z) = y
  write.table(t(z),filename,sep="\t",col.names=NA)
  
  pdf(paste(filename,".pdf",sep=""))
  filled.contour(x,y,z,main=filename,xlab="End-to-End Distance (Å)",ylab="Alpha Helicity", color.palette=colorRampPalette(c("red","yellow","green","cyan","blue","purple")), nlevels = 100)
  dev.off()
  
  z[is.infinite(z)] = max(G) # level infinite values for pretty plotting
  pdf(paste(filename,"_pretty",".pdf",sep=""))
  filled.contour(x,y,z,main=filename,xlab="End-to-End Distance (Å)",ylab="Alpha Helicity", color.palette=colorRampPalette(c("red","yellow","green","cyan","blue","purple")), nlevels = 100)
  dev.off()
  
}  