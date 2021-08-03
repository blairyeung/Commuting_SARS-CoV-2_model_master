library(reshape2)
library(viridis)
library(tidyverse)
library(tidyquant)
library(ggplot2)

data <- read.csv('G:/R/vaccines_by_age.csv')

data2<- melt(data)

ggplot(data = data, aes(x = Agegroup, y = as.Date(Date))) +
  geom_raster(aes(fill = Percent_fully_vaccinated)) +
  coord_flip() + 
  scale_fill_gradient(low = 'yellow', high = 'blue', trans = 'pseudo_log')+ 
  ylab('Date') + theme_minimal()
 
ggplot(data = data2, aes(x = Agegroup, y = as.Date(Date))) +
  geom_raster(aes(fill = value)) +
  coord_flip() + 
  scale_fill_gradient(low = 'yellow', high = 'blue', trans = 'pseudo_log')+ 
  ylab('Date') + theme_minimal() + facet_wrap(~ variable)

ggplot(data = data2, aes(x = Agegroup, y = as.Date(Date))) +
  geom_raster(aes(fill = value)) +
  coord_flip() + 
  ylab('Date') + theme_minimal() + facet_wrap(~ variable) + 
  scale_fill_gradient2(low = 'white', mid = '#e0c000', high = '#ee2e24', midpoint = 0.4, limits = c(0,1))+
  scale_y_date(date_labels = "%Y%B", date_breaks = '2 month') +
  theme(axis.text.x = element_text(vjust = 0)) +
  theme(strip.text.x = element_text(size = 12))

ggplot(data = data2, aes(x = as.Date(Date), y = value)) +
  geom_area(position = 'stack', aes(fill = Agegroup, alpha = I(0.5))) +
  geom_line(position = 'stack', aes(color = Agegroup, size = I(1.02))) + 
  xlab('Date') + 
  facet_wrap(~ variable, scales = 'free_y') + 
  theme(strip.text.x = element_text(size = 12)) + 
  scale_fill_brewer(palette = "Pastel1") +
  scale_color_brewer(palette = "Pastel1")
 
