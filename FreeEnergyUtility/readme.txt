/*****************************************************************************/
/*************************Free Energy Surface Utility*************************/
/*****************************************************************************/

Author: Scott Gigante, scottgigante@gmail.com
Since: Nov 2014

This is a tool which generates a free energy surface, associated error and 
most likely folding path from a table of transition counts. On current set-up,
the x axis of the free energy surface is always made up of integer bins and
the y axis of floating point bins between 0 and 1.

How to install:
copy pdf, dat, r, minimumFreeEnergy.jar
cd r
module load R
R
source("setup.r")

How to run:
cd r
module load R
module load java/1.8.0
R
source("runFreeEnergyUtility.r")

Input arguments are stored in dat/readfile.dat

Maintenance:
If any modifications to the Java project MinimumFreeEnergyPath, export Java 
project to MinimumFreeEnergyPath.jar as Runnable JAR file. This project 
publicly available at 
https://github.com/scottgigante/SWEN20030/tree/master/FreeEnergyUtility