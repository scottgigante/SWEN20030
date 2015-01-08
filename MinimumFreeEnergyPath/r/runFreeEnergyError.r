## Runs FreeEnergyError.r from command line arguments specified in dat/readfile.dat
## Scott Gigante, scottgigante@gmail.com, December 2014

filename = "../dat/readfile.dat"
args = readLines(filename)
commandArgs = function(trailingOnly) args
source("FreeEnergyError.r")
