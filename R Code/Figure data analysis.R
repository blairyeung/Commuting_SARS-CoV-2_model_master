library(ggplot2)
library(tidyr)
library(reshape2)

cases_in_ontario <- read.csv('G:/R/cases_by_status_and_phu_population.csv')
newdata <- na.omit(cases_in_ontario)

newdata2 <-melt(newdata)
newdata2 <- na.omit(newdata2)

colors <- c('#b30838','#ee2e24','#ffad59','#e0c000')
colors2 <- c('#ee2e24','#6cb33f','#009fc2','#936fb1')

ggplot(data = newdata2, aes(x = as.Date(FILE_DATE))) +
  geom_line(aes(color = variable, y = value, alpha = I(0.8))) + 
  facet_wrap(~ PHU_NAME,scales = 'free_y') + 
  theme(axis.text.x = element_text(angle = 45, hjust = 1)) + xlab('Date') +
  scale_y_continuous("Active_cases", 
    sec.axis = sec_axis(~ . * 0.05, name = "Deaths")) +
  scale_color_manual(values = c('#ee2e24','#009fc2'))+ 
  theme(legend.position="top")


