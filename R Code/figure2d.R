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
colnames(bigdata) <- c("Generation","0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+","Scenario")
#bigdata <- melt(bigdata, id.vars = "Generation")
#colnames(bigdata) <- c("Generation","Age_group_of_prioritization","Number_of_deaths_reduced")

bigdata <- melt(bigdata, id.vars = "Scenario")
colnames(bigdata) <- c("Scenario","Generation","Age_group_of_prioritization","Number_of_deaths_reduced")

for (i in c(2:5)) {
  Path <- "E:/Global Model/Synthesized Matrix"
  SurPath <- paste(Categories[i], ".csv" , sep = "")
  Path <- paste(Path,SurPath, sep = "")
  print(Path)
  DeathCaseMat <- read.csv(Path)
  colnames(DeathCaseMat) <- c("Generation","0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+")
  DeathCaseMat <- melt(DeathCaseMat, id.vars = "Generation")
  colnames(DeathCaseMat) <- c("Generation","Age_group_of_prioritization","Number_of_deaths_reduced")
  bigdata <- rbind(bigdata,DeathCaseMat)
}
write.csv(bigdata, file = "E:/Global Model/Synth.csv")

Fa <- c(1:21)
for (i in c(1:21)) {
  print(paste("Gen", as.character(i), sep = "_"))
  Fa[i] <- (paste("Gen", as.character(i), sep = "_"))
}


scale_color_manual(values = c("#b30838","#ee2e24","#ffad59","#e0c000","#91bda6"))

bigdata <- read.csv("E:/Global Model/Synth.csv")
#bigdata$Generation<- factor(bigdata$Generation, levels = Fa)


DeathCaseLine <- ggplot(bigdata, aes(group = 1)) +
  geom_ribbon(aes(x = Generation, ymin= 0.75* as.numeric(Number_of_deaths_reduced), ymax= 2.76 * as.numeric(Number_of_deaths_reduced), fill=Age_group_of_prioritization, alpha = I(0.2))) +
  geom_line(aes(x = Generation, y = as.numeric(Number_of_deaths_reduced), color = Age_group_of_prioritization , size = I(1.2))) +
  scale_y_continuous(trans = "log10", limits = (c(0.005,500)))+
  theme(axis.text.y=element_text(size=9), plot.title=element_text(size=11)) + theme(legend.key=element_blank()) + labs(x = "Generation", y = "Number_of_deaths_reduced")
DeathCaseLine = DeathCaseLine +  theme(axis.text.x=element_text(size=9, angle=45, vjust=0.3),
                                       axis.text.y=element_text(size=9),
                                       plot.title=element_text(size=11)) + facet_grid(Tier ~ Age_group_of_prioritization) + theme(legend.position = "none")

DeathCaseLine

