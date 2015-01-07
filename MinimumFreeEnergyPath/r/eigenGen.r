eigenTest = function(T,w,q_sd,samples=100) {

  #constants
  T1 = 5
  T2 = 6
  SAMPLE = samples

  n=nrow(T)
  R = sqrt(T*(1-T)/(w+rep(1,n)))  
  q = abs(Re(eigen(t(T))$vectors[,1]))
  
  qu= q+q_sd
  ql= q-q_sd

  Tl = T-R
  Tu= T+R
  pdf("error_test.pdf")
  plot(c(qu[T1],ql[T1]),c(qu[T2],ql[T2]),xlim=c(min(c(qu[T1],ql[T1]))-abs(qu[T1]-ql[T1]),max(c(qu[T1],ql[T1]))+abs(qu[T1]-ql[T1])), ylim=c(min(c(qu[T2],ql[T2]))-abs(qu[T2]-ql[T2]),max(c(qu[T2],ql[T2]))+abs(qu[T2]-ql[T2])),col=2)
       
  abline(v=qu[T1],col=2)
  abline(h=qu[T2],col=2)
  abline(v=ql[T1],col=2)
  abline(h=ql[T2],col=2)

  TSample = array(0,dim=c(n,n,SAMPLE))
  qSample = matrix(0,nrow=n,ncol=SAMPLE)

  for (i in 1:n) {
    for (j in 1:n) {
      if (T[i,j] != 0) {
        alpha = -(T[i,j]*(R[i,j]^2+T[i,j]^2-T[i,j]))/(R[i,j]^2)
        beta = (R[i,j]^2+T[i,j]^2-T[i,j])*(T[i,j]-1)/(R[i,j]^2)
        TSample[i,j,]=rbeta(SAMPLE,alpha,beta)
      }
    }
  }

  for (k in 1:SAMPLE) {
    w = rowSums(TSample[,,k])
    w[sapply(w, function(row) sum(row)==0)] = 1
    TSample[,,k] = TSample[,,k]/w
  }
  
  for (i in 1:SAMPLE) {
    A=TSample[,,i]
    qSample[,i]=abs(Re(eigen(t(A))$vectors[,1]))
    points(qSample[T1,i],qSample[T2,i])
  }

  dev.off()
  
  non_zero = apply(qSample,1,function(row) sum(row)!=0)

  mean = rowSums(qSample)/SAMPLE 
  sd = rep(0,length(q[non_zero]))
  for (i in 1:length(sd)) {
    sd[i] = sd(qSample[i,])
  }

  result=matrix(0,nrow=length(q[non_zero]),ncol=5)
  result[,1] = q[non_zero]
  result[,2] = mean[non_zero]
  result[,3] = q_sd[non_zero]
  result[,4] = sd
  result[,5] = (q[non_zero] <= mean[non_zero]+sd[non_zero] & q[non_zero] >= mean[non_zero]-sd[non_zero])
  colnames(result) = c("E(q)","q","E(sd)","sd","q-sd < E(q) < q+sd")
  return(result)
}
