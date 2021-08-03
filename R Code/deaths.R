#Raster
library(ggplot2)
library(tidyr)
library(reshape2)

#cases_in_ontario <- read.csv('G:/R/cases_by_status_and_phu.csv')
cases_in_ontario <- read.csv('G:/R/cases_by_status_and_phu_population.csv')
newdata <- na.omit(cases_in_ontario)
colnames(newdata) <- c('FILE_DATE','PHU_NAME','Estimated_immunity_level','Weekly_incidence')
newdata2 <-melt(newdata)
newdata2 <- na.omit(newdata2)
colnames(newdata2) <- c('FILE_DATE','PHU_NAME','variable','value')

ggplot(data = newdata2, aes(x = PHU_NAME, y = as.Date(FILE_DATE))) +
  geom_raster(aes(fill = value)) +
  coord_flip() + 
  scale_fill_gradient(low = 'yellow', high = 'blue', trans = 'pseudo_log', breaks = c(0, 10, 100, 1000, 10000))+ 
  facet_wrap(~ variable, nrow = 1) + 
  ylab('Date') + theme_minimal()
  
ggplot(data = newdata, aes(x = PHU_NAME, y = as.Date(FILE_DATE))) +
  geom_raster(aes(fill = Estimated_immunity_level)) + 
  scale_fill_gradient2(low = 'white', high = '#ee2e24', mid = '#e0c000',midpoint = 4, trans = 'pseudo_log', breaks = c(0, 10, 100, 1000, 10000))+ 
  ylab('Date') +
  coord_flip() + theme_minimal() +
  theme(axis.text.x = element_text(angle = 45, hjust = 1))


ggplot(data = newdata, aes(x = PHU_NAME, y = as.Date(FILE_DATE))) +
  geom_raster(aes(fill = Weekly_incidence)) + 
  scale_fill_gradient(low = '#ffff80', high = '#6b0000')+ 
  ylab('Date') +
  coord_flip() + theme_minimal() +
  theme(axis.text.x = element_text(angle = 45, hjust = 1))

ggplot(data = newdata2, aes(x = PHU_NAME, y = as.Date(FILE_DATE))) +
  geom_raster(aes(fill = value)) +
  coord_flip() + 
  scale_fill_gradient2(low = 'white', high = '#ee2e24', mid = '#e0c000',midpoint = 4, trans = 'pseudo_log', breaks = c(0, 10, 100, 1000, 10000, 100000))+ 
  facet_wrap(~ variable, nrow = 1) + 
    ylab('Date') + theme_minimal() + theme(strip.text.x = element_text( size = 16))

ggplot(data = newdata2, aes(x = PHU_NAME, y = as.Date(FILE_DATE))) +
  geom_raster(aes(fill = value)) +
  coord_flip() + 
  scale_fill_gradient(low = '#ffff80', high = '#6b0000')+ 
  facet_wrap(~ variable, nrow = 1) + 
  ylab('Date') + theme_minimal()

ggplot(data = newdata2, aes(x = PHU_NAME, y = as.Date(FILE_DATE))) +
  geom_raster(aes(fill = value)) +
  coord_flip() + 
  scale_fill_gradient(trans = 'pseudo_log', breaks = c(0, 10, 100, 1000, 10000, 100000))+ 
  facet_wrap(~ variable, nrow = 1) + 
  ylab('Date') + theme_minimal() + theme(strip.text.x = element_text( size = 16))
