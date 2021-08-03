library(reshape2)
library(ggplot2)
library(ggpubr)
library(cowplot)

vaccine_death_data <- read.csv('G:/R/Vaccine_against_death2.csv')

vaccine_data_data_2 <- melt(vaccine_death_data)

colors <- c('#b30838','#ffad59','#e0c000')
colors2 <- c('#ee2e24','#009fc2','#936fb1')

Vaccine_data <- read.csv('G:/R/Vaccine_against_death_melt.csv')

#write.csv(vaccine_data_data_2, file = 'G:/R/Vaccine_against_death_melt.csv')

colnames(Vaccine_data) = c('Vaccine','Population','Dose','Type','VE_infection_median','VE_infection_low','VE_infection_high','Days_after_vaccination','Deviation')


ggplot(data = Vaccine_data, aes(x = Days_after_vaccination)) +
  geom_jitter(aes(y = VE_Agaisnt_infection)) +
  geom_smooth(method = 'lm', aes(y = VE_Agaisnt_infection), ) +
  facet_grid (Dose ~ Type) 


ggplot(data = Vaccine_data, aes(x = Days_after_vaccination)) +
  geom_boxplot(aes(x = Days_after_vaccination, middle = VE_Agaisnt_infection, upper = VE_infection_high, lower = VE_infection_low)) +
  geom_smooth(method = 'lm', aes(y = VE_Agaisnt_infection), ) +
  facet_grid (Dose ~ Type) 

width <- 5

pl <- ggplot(data = Vaccine_data, aes(x=Days_after_vaccination))+
  geom_linerange(aes(x=Days_after_vaccination + Deviation, ymin=VE_infection_low, ymax=VE_infection_high, color = Population)) +
  geom_rect(aes(xmin = Deviation + Days_after_vaccination - width/2 * 0.9, xmax = Deviation + Days_after_vaccination + width/2 * 0.9, ymin = VE_infection_low * 1.05, ymax = VE_infection_high * 0.95, alpha = I(0.6), fill = Population)) +
  geom_hline(yintercept  = 1, size = I(1.2), linetype = "longdash") +
  scale_fill_manual(values = c('#ee2e24','#e0c000','#6cb33f','#009fc2')) +
  scale_color_manual(values = c('#ee2e24','#e0c000','#6cb33f','#009fc2')) +
  facet_wrap (Dose ~ Type) + ylab('Vaccine_efficacy') + 
  theme(legend.position = 'bottom')+ 
  scale_fill_manual(values = colors2) +
  scale_color_manual(values = colors2)


pl

p2 <- ggplot(data = Vaccine_data, aes(x=Days_after_vaccination))+
  geom_linerange(aes(x=Days_after_vaccination + Deviation, ymin=VE_infection_low, ymax=VE_infection_high, color = Population)) +
  geom_rect(aes(xmin = Deviation + Days_after_vaccination - width/2 * 0.9, xmax = Deviation + Days_after_vaccination + width/2 * 0.9, ymin = VE_infection_low * 1.05, ymax = VE_infection_high * 0.95, alpha = I(0.6), fill = Population)) +
  geom_hline(yintercept  = 1, size = I(1.2), linetype = "longdash") +
  scale_fill_manual(values = c('#ee2e24','#e0c000','#6cb33f','#009fc2')) +
  scale_color_manual(values = c('#ee2e24','#e0c000','#6cb33f','#009fc2')) +
  facet_wrap (~ Dose) + ylab('Vaccine_efficacy') +
  theme(legend.position = 'bottom')+ 
  scale_fill_manual(values = colors) +
  scale_color_manual(values = colors) +
  scale_y_continuous(limits = c(0,1))+
  scale_y_continuous(limits = c(0,1)) +
  scale_x_continuous(limits = c(0,45))


p2
