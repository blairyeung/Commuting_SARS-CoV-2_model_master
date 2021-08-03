#Commuting Heat map
library(reshape2)
library(viridis)
library(tidyverse)
library(tidyquant)
library(ggplot2)

Commute_data <- read.csv('G:/R/District_Importing.csv')

sets <- c(1140,2030,4320,6375,9155,12495,41730,88930,146200,207385,311250)

ggplot(data = Commute_data, aes(x = District, y = 1)) +
  geom_raster(aes(fill = Imported)) +
  scale_fill_gradient(low = '#ffff80', high = '#6b0000', trans = 'log10')
  
Commute_data_import <- read.csv('G:/R/District_Exporting.csv')

ggplot(data = Commute_data_import, aes(x = District, y = 1)) +
  geom_raster(aes(fill = Imported)) +
  scale_fill_gradient(low = '#ffff80', high = '#6b0000', trans = 'log10')

