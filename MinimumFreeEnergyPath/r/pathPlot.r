library(ggplot2)

filename = "../dat/free_energy_select_above_delete_yes_10_not-normalized.dat_path.dat"

path = read.table(filename)
x = c(0:(nrow(path)-1))
y = path[,3]

plot = qplot(x, y, geom="line",xlab="Path Length (bins)",ylab="Gibbs Free Energy (kcal/mol)", color=y)
ggsave(sprintf("../dat/%s.pdf",filename), plot)