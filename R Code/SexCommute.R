#Commuter_sex

library(tidyverse)
library(viridis)
library(patchwork)
library(networkD3)
library(ggplot2)
library(ggalluvial)

Sex_commute <- read.csv('K:/Ontario Model/FullSankey.csv')

colnames(Sex_commute) <- c('Resident','Work',"Commuters","Male_commuter","Female_commuter","Distance","Type","Resident_district_code","Work_district_code")

ggplot(data = Sex_commute, aes(x = Male_commuter, y = Female_commuter, color = Distance)) +
  geom_line(data = Sex_commute, aes(x=Male_commuter, y = Male_commuter), color = "black") +
  geom_point(aes(alpha = I(0.4), size = I(log10(Commuters)))) + scale_x_continuous(trans = 'pseudo_log') + 
  scale_y_continuous(trans = 'pseudo_log') +
  scale_color_gradient(trans = 'pseudo_log',high = '#e0c000',low = '#b30838', limits = c(1,180),breaks = c(180,60,20,5,1), oob = scales::squish) +
  facet_wrap(~Type, nrow =2, scale = 'free')

ggplot(data = Sex_commute, aes(x = Male_commuter, y = Female_commuter, color = Distance)) +
  geom_line(data = Sex_commute, aes(x=Male_commuter, y = Male_commuter), color = "black") +
  geom_point(aes(alpha = I(0.4), size = I(log10(Commuters)), shape = Type)) + scale_x_continuous(trans = 'pseudo_log') + 
  scale_y_continuous(trans = 'pseudo_log') +
  scale_shape_manual(values = c(17, 18, 15, 19))+
  scale_color_gradient(trans = 'pseudo_log',high = '#e0c000',low = '#b30838', limits = c(1,180),breaks = c(180,60,20,5,1), oob = scales::squish)


colors <- c('#ffad59','#ee2e24','#009fc2','#00549e')

ggplot(data = Sex_commute, aes(x = Distance, y = Commuters, color = Type)) +
  geom_point(aes(alpha = I(0.4), size = I(2))) + scale_x_continuous(trans = 'pseudo_log') + 
  scale_y_continuous(trans = 'pseudo_log') +
  facet_wrap(~Type, nrow =2) + scale_color_manual(values = colors)



is_alluvia_form(as.data.frame(Sex_commute), axes = 1:3, silent = TRUE)




head(as.data.frame(Sex_commute), n = 12)

ggplot(as.data.frame(Sex_commute), aes(y = Commuters, axis1 = Resident, axis2 = Work)) +
  geom_alluvium(width = 1/12, aes(fill = Type, alpha = I(0.5))) +
  geom_stratum(width = 1/12, fill = "white", color = "grey") +
  geom_text(stat = "stratum", aes(label = after_stat(stratum), size = I(3)), check_overlap=TRUE) +
  scale_x_discrete(limits = c("Resident", "Work"), expand = c(.05, .05)) +
  scale_fill_manual(values = colors) + theme_light() +
  facet_wrap(~Resident_district_code, nrow = 7, scales = 'free') 

ggplot(as.data.frame(Sex_commute), aes(y = Commuters, axis1 = Resident_district_code, axis2 = Work_district_code)) +
  geom_alluvium(width = 1/12, aes(fill = abs(Resident_district_code-Work_district_code), alpha = I(0.5))) +
  geom_stratum(width = 1/12, fill = "white", color = "grey") +
  geom_text(stat = "stratum", aes(label = after_stat(stratum), size = I(3)), check_overlap=TRUE) +
  scale_fill_gradient(high = '#ee2e24',low = '#e0c000') +
  facet_wrap(~Type, nrow = 2, scales = 'free')


ggplot(as.data.frame(Sex_commute), aes(y = Commuters, axis1 = Resident_district_code, axis2 = Work_district_code)) +
  geom_alluvium(width = 1/12, aes(fill = Distance, alpha = I(0.5))) +
  geom_stratum(width = 1/12, fill = "white", color = "grey") +
  geom_text(stat = "stratum", aes(label = after_stat(stratum), size = I(3)), check_overlap=TRUE) +
  scale_fill_gradient(trans = 'pseudo_log',high = '#ee2e24',low = '#00549e', limits = c(1,180),breaks = c(180,60,20,5,1), oob = scales::squish) +
  facet_wrap(~Type, nrow = 2, scales = 'free')

  scale_x_discrete(limits = c("Resident", "Work"), expand = c(.05, .05)) +
  #scale_fill_manual(values = colors) + theme_light() +
 # facet_wrap(~Type, nrow = 2, scales = 'free')

fileraster <- read.csv('K:/Model IO/IO Commute.csv')

colnames(fileraster) <- c('Resident','Resident_code','Work','Work_Code','Commuters','Male','Female')

ggplot(as.data.frame(fileraster), aes(x = Resident, y = Work)) +
  geom_raster(aes(fill = Commuters))
