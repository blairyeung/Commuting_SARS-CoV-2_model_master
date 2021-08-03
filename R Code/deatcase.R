library(ggplot2)
library(reshape2)

DeathCaseMat <- read.csv("E:/Global Model/Synthesized MatrixLockdown .csv")
colnames(DeathCaseMat) <- c("Type","0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+")
DeathCaseMat <- melt(DeathCaseMat)
colnames(DeathCaseMat) <- c("Generation","Age_group_of_prioritization","Number_of_cases_reduced")

#DeathCaseMat$Age_group_of_individual<- factor(DeathCaseMat$Age_group_of_individual, levels = Lbs)
#DeathCaseMat$Age_group_of_contact<- factor(DeathCaseMat$Age_group_of_contact, levels = Lbs)

Fa <- c(0:99)
for (i in c(0:99)) {
  print(paste("Gen", as.character(i), sep = "_"))
  Fa[i] <- (paste("Gen", as.character(i), sep = "_"))
}

DeathCaseMat$Generation<- factor(DeathCaseMat$Generation, levels = Fa)

DeathCaseMatrix <- ggplot(DeathCaseMat, aes(x = Generation, y = Age_group_of_prioritization)) +
  geom_raster(aes(fill=Number_of_cases_reduced)) +  scale_fill_gradient(low="white", high= "#00703c", trans="log10") +
  theme(axis.text.y=element_text(size=9), plot.title=element_text(size=11)) + theme(legend.key=element_blank()) + labs(x = "Generation", y ="Age_group_of_prioritization") +   theme(panel.background = element_rect(fill='white'), panel.grid.major = element_blank(),panel.grid.minor = element_blank(),panel.border = element_blank()) + theme(strip.background = element_rect(fill="white"),  strip.text.x = element_text(size = 18.25))
DeathCaseMatrix = DeathCaseMatrix +  theme(axis.text.x=element_text(size=9, angle=45, vjust=0.3),
                                     axis.text.y=element_text(size=9),
                                     plot.title=element_text(size=11)) + theme(legend.key=element_blank()) + 
  coord_flip()
DeathCaseMatrix
