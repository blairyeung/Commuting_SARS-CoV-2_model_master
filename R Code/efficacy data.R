#Fitting the efficacy of vaccines
library(tidyverse)
library(ggplot2)

vaccinedata <- read.csv('G:/R/Vaccine.csv')

colnames(vaccinedata) <- c('Analysis_Period', 'Days_aft_first_dose', 'Vaccine', 'Placebo','Efficacy_original','Low','High','Efficacy','low_fit','high_fit')

ggplot(data = vaccinedata, aes(x = Days_aft_first_dose)) +
  geom_ribbon(aes(y = Efficacy, ymin = low_fit, ymax = high_fit), alpha = I(0.2), fill = 'blue') +
  geom_line(aes(y = Efficacy), size = I(1.5)) + 
  geom_point(aes(y = Efficacy_original), shape = 3, size = I(5), color = "red")
              
