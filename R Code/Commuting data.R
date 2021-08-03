#Commuting data
install.packages("ggalluvial")
install.packages("ggplot2")
install.packages("dplyr")
install.packages("networkD3")
install.packages("riverplot")
install.packages("ggalluvial")

library(tidyverse)
library(viridis)
library(patchwork)
library(networkD3)
library(ggplot2)
library(ggalluvial)


nodes = read.csv('K:/Ontario Model/Sankey_Index.csv')

Dataset = read.csv('K:/Ontario Model/Book3.csv')


colnames(Dataset) = c('Resident','Work',"Commuters","Distance")

Dataset2 <- to_lodes_form(data.frame(Dataset), key = "Demographic", axes = 1:2)

is_alluvia_form(as.data.frame(Dataset), axes = 1:3, silent = TRUE)


head(as.data.frame(Dataset), n = 12)

ggplot(as.data.frame(Dataset), aes(y = Commuters, axis1 = Resident, axis2 = Work)) +
  geom_alluvium(width = 1/12, aes(fill = Distance, alpha = I(0.5))) +
  geom_stratum(width = 1/12, fill = "white", color = "grey") +
  geom_text(stat = "stratum", aes(label = after_stat(stratum))) +
  scale_x_discrete(limits = c("Resident", "Work"), expand = c(.05, .05)) +
  scale_fill_gradient(low = 'blue',high = 'grey')
