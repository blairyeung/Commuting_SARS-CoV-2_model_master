library(reshape2)
library(ggplot2)
library(ggpubr)
library(cowplot)

Categories <- c("Lockdown","Control","Restrict","Protect","Prevent")

Path <- "E:/Global Model/Synthesized Matrix"
SurPath <- paste(Categories[1], ".csv" , sep = "")
Path <- paste(Path,SurPath, sep = "")
print(Path)
bigdata <- read.csv(Path)
colnames(bigdata) <- c("Type","0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+","Scenario")
bigdata <- melt(bigdata)
colnames(bigdata) <- c("Generation","Scenario","Age_group_of_prioritization","Number_of_deaths_reduced")

for (i in c(2:5)) {
  Path <- "E:/Global Model/Synthesized Matrix"
  SurPath <- paste(Categories[i], ".csv" , sep = "")
  Path <- paste(Path,SurPath, sep = "")
  print(Path)
  DeathCaseMat <- read.csv(Path)
  colnames(DeathCaseMat) <- c("Type","0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+")
  DeathCaseMat <- melt(DeathCaseMat)
  colnames(DeathCaseMat) <- c("Generation","Scenario","Age_group_of_prioritization","Number_of_deaths_reduced")
  bigdata <- rbind(bigdata,DeathCaseMat)
}

Fa <- c(0:99)
for (i in c(0:99)) {
  print(paste("Gen", as.character(i), sep = "_"))
  Fa[i] <- (paste("Gen", as.character(i), sep = "_"))
}

bigdata$Generation<- factor(DeathCaseMat$Generation, levels = Fa)

DeathCaseMatrix <- ggplot(bigdata, aes(x = Generation, y = Age_group_of_prioritization)) +
  geom_raster(aes(fill=Number_of_deaths_reduced)) +  scale_fill_gradient(low="white", high= "#00703c", trans="log10", limits = c(0.005,(80)))+
  theme(axis.text.y=element_text(size=9), plot.title=element_text(size=11)) + theme(legend.key=element_blank()) + labs(x = "Generation", y ="Age_group_of_prioritzation") +   theme(panel.background = element_rect(fill='white'), panel.grid.major = element_blank(),panel.grid.minor = element_blank(),panel.border = element_blank()) + theme(strip.background = element_rect(fill="white"),  strip.text.x = element_text(size = 18.25))
DeathCaseMatrix = DeathCaseMatrix +  theme(axis.text.x=element_text(size=9, angle=45, vjust=0.3),
                                           axis.text.y=element_text(size=9),
                                           plot.title=element_text(size=11)) + theme(legend.key=element_blank()) + 
  coord_flip() + facet_wrap(~ Scenario, nrow = 1)
print(max(bigdata$Number_of_deaths_reduced))
DeathCaseMatrix

Path <- "E:/Global Model/Synthesized Matrix"
SurPath <- paste(Categories[4], ".csv" , sep = "")
Path <- paste(Path,SurPath, sep = "")
print(Path)
bigdata <- read.csv(Path)
colnames(bigdata) <- c("Type","0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+")
bigdata <- melt(bigdata)
colnames(bigdata) <- c("Generation","Scenario","Age_group_of_prioritization","Number_of_deaths_reduced")

for (i in c(4:5)) {
  Path <- "E:/Global Model/Synthesized Matrix"
  SurPath <- paste(Categories[i], ".csv" , sep = "")
  Path <- paste(Path,SurPath, sep = "")
  print(Path)
  DeathCaseMat <- read.csv(Path)
  colnames(DeathCaseMat) <- c("Type","0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+")
  DeathCaseMat <- melt(DeathCaseMat)
  colnames(DeathCaseMat) <- c("Generation","Scenario","Age_group_of_prioritization","Number_of_deaths_reduced")
  bigdata <- rbind(bigdata,DeathCaseMat)
}

Fa <- c(0:99)
for (i in c(0:99)) {
  print(paste("Gen", as.character(i), sep = "_"))
  Fa[i] <- (paste("Gen", as.character(i), sep = "_"))
}

bigdata$Generation<- factor(DeathCaseMat$Generation, levels = Fa)

DeathCaseMatrix2 <- ggplot(bigdata, aes(x = Generation, y = Age_group_of_prioritization)) +
  geom_raster(aes(fill=Number_of_deaths_reduced)) +  scale_fill_gradient(low="white", high= "#00703c", trans="log10", limits = c(0.005,80)) +
  theme(axis.text.y=element_text(size=9), plot.title=element_text(size=11)) + theme(legend.key=element_blank()) + labs(x = "Generation", y ="Age_group_of_prioritization") +   theme(panel.background = element_rect(fill='white'), panel.grid.major = element_blank(),panel.grid.minor = element_blank(),panel.border = element_blank()) + theme(strip.background = element_rect(fill="white"),  strip.text.x = element_text(size = 18.25))
DeathCaseMatrix2 = DeathCaseMatrix2 +  theme(axis.text.x=element_text(size=9, angle=45, vjust=0.3),
                                           axis.text.y=element_text(size=9),
                                           plot.title=element_text(size=11)) + theme(legend.key=element_blank()) + 
  coord_flip() + facet_wrap(~ Scenario, nrow = 1) + theme(legend.position = "none") + theme(axis.line.y = element_blank(), axis.text.y = element_blank())

DeathCaseMatrix2 



Path <- "E:/Global Model/Synthesized Matrix"
SurPath <- paste(Categories[3], ".csv" , sep = "")
Path <- paste(Path,SurPath, sep = "")
print(Path)
bigdata <- read.csv(Path)
colnames(bigdata) <- c("Type","0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+")
bigdata <- melt(bigdata)
colnames(bigdata) <- c("Generation","Scenario","Age_group_of_prioritization","Number_of_deaths_reduced")

Fa <- c(0:99)
for (i in c(0:99)) {
  print(paste("Gen", as.character(i), sep = "_"))
  Fa[i] <- (paste("Gen", as.character(i), sep = "_"))
}

bigdata$Generation<- factor(DeathCaseMat$Generation, levels = Fa)

DeathCaseMatrix3 <- ggplot(bigdata, aes(x = Generation, y = Age_group_of_prioritization)) +
  geom_raster(aes(fill=Number_of_deaths_reduced)) +  scale_fill_gradient(low="white", high= "#00703c", trans="log10", limits = c(0.005,80)) +
  theme(axis.text.y=element_text(size=9), plot.title=element_text(size=11)) + theme(legend.key=element_blank()) + labs(x = "", y ="Age_group_of_prioritization") +   theme(panel.background = element_rect(fill='white'), panel.grid.major = element_blank(),panel.grid.minor = element_blank(),panel.border = element_blank()) + theme(strip.background = element_rect(fill="white"),  strip.text.x = element_text(size = 18.25))
DeathCaseMatrix3 = DeathCaseMatrix3 +  theme(axis.text.x=element_text(size=9, angle=45, vjust=0.3),
                                             axis.text.y=element_text(size=9),
                                             plot.title=element_text(size=11)) + 
  theme(legend.key=element_blank()) + 
  facet_wrap(~ Scenario, nrow = 1) + 
  theme(legend.position = "none") + 
  theme(axis.line.y = element_blank(), axis.text.y = element_blank()) + 
  coord_flip()

DeathCaseMatrix3

ggarrange(DeathCaseMatrix,DeathCaseMatrix3,DeathCaseMatrix2, nrow =1)

Father <- ggdraw() +
  draw_plot(DeathCaseMatrix2, x = 0, y = 0, width = 0.4) +
  draw_plot(DeathCaseMatrix3, x = .4, y = 0, width = 0.2) +
  draw_plot(DeathCaseMatrix, x = .6, y = 0, width = 0.4)

Father


DeathCaseLine <- ggplot(bigdata, aes(x = Generation, y = Number_of_deaths_reduced)) +
  geom_line(aes(fill=Number_of_deaths_reduced)) +  scale_fill_gradient(low="white", high= "#00703c", trans="log10", limits = c(0.005,80)) +
  theme(axis.text.y=element_text(size=9), plot.title=element_text(size=11)) + theme(legend.key=element_blank()) + labs(x = "Generation") +   theme(panel.background = element_rect(fill='white'), panel.grid.major = element_blank(),panel.grid.minor = element_blank(),panel.border = element_blank()) + theme(strip.background = element_rect(fill="white"),  strip.text.x = element_text(size = 18.25))
DeathCaseLine = DeathCaseLine +  theme(axis.text.x=element_text(size=9, angle=45, vjust=0.3),
                                           axis.text.y=element_text(size=9),
                                           plot.title=element_text(size=11))
DeathCaseLine
