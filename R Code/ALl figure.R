
library(tidyverse)
library(viridis)
library(patchwork)
library(networkD3)
library(ggplot2)
library(ggalluvial)

#Sankey by county
for (i in c(0:548)) {
  print(i)
  path1 <- 'K:/Ontario Model/Sankey_fig2/'
  path2 <- i
  path3 <- '.csv'
  path <- paste(path1,path2,path3,sep ="")
  print(path)
  
  outputpath1 <- 'G:/Figures/Sankey_by_county/'
  outputpath3 <- '.pdf'
  outputpath <- paste(outputpath1,path2,outputpath3, sep="")
  print(outputpath)

  Dataset = read.csv(path)
  #colnames(Dataset) = c('Resident','Work',"Commuters","Distance","Type")
  colnames(Dataset) = c('Resident','Work',"Commuters","Male","Female","Distance", "Type")
  print(nrow(Dataset))
  
  if(nrow(Dataset[1])>1){
    p <- ggplot(as.data.frame(Dataset), aes(y = Commuters, axis1 = Resident, axis2 = Work)) +
      geom_alluvium(width = 1/12, aes(fill = Type)) +
      geom_stratum(width = 1/12, fill = "white", color = "grey") +
      geom_text(stat = "stratum", aes(label = after_stat(stratum))) +
      scale_x_discrete(limits = c("Resident", "Work"), expand = c(.05, .05)) + theme_light() 
  
    
      ggsave(outputpath, plot = p, device = "pdf", width = 12, height = 8)
  }
}


#Sankey by district
for (i in c(0:48)) {
  print(i)
  path1 <- 'K:/Ontario Model/'
  path2 <- i
  path3 <- '.csv'
  path <- paste(path1,path2,path3,sep ="")
  print(path)
  
  outputpath1 <- 'G:/Figures/Sankey_by_district/'
  outputpath3 <- '.pdf'
  outputpath <- paste(outputpath1,path2,outputpath3, sep="")
  print(outputpath)
  
  Dataset = read.csv(path)
  #colnames(Dataset) = c('Resident','Work',"Commuters","Distance","Type")
  colnames(Dataset) = c('Resident','Work',"Commuters","Male","Female","Distance", "Type")
  print(nrow(Dataset))
  
  if(nrow(Dataset[1])>1){
    p <- ggplot(as.data.frame(Dataset), aes(y = Commuters, axis1 = Resident, axis2 = Work)) +
      geom_alluvium(width = 1/12, aes(fill = Type)) +
      geom_stratum(width = 1/12, fill = "white", color = "grey") +
      geom_text(stat = "stratum", aes(label = after_stat(stratum))) +
      scale_x_discrete(limits = c("Resident", "Work"), expand = c(.05, .05)) + theme_light() 
    
    
    ggsave(outputpath, plot = p, device = "pdf", width = 12, height = 8)
  }
}



 
nodes = read.csv('K:/Ontario Model/Sankey_Index.csv')

Dataset = read.csv('K:/Ontario Model/Sankey.csv')


colnames(Dataset) = c('Resident','Work',"Commuters","Male","Female","Distance")

Dataset2 <- to_lodes_form(data.frame(Dataset), key = "Demographic", axes = 1:2)

is_alluvia_form(as.data.frame(Dataset), axes = 1:3, silent = TRUE)


head(as.data.frame(Dataset), n = 12)

ggplot(as.data.frame(Dataset), aes(y = Commuters, axis1 = Resident, axis2 = Work)) +
  geom_alluvium(width = 1/12, aes(fill = Distance, alpha = I(0.5))) +
  geom_stratum(width = 1/12, fill = "white", color = "grey") +
  geom_text(stat = "stratum", aes(label = after_stat(stratum))) +
  scale_x_discrete(limits = c("Resident", "Work"), expand = c(.05, .05)) +
  scale_fill_gradient(high = '#e0c000',low = '#b30838', limits = c(0,120)) + theme_light() 
