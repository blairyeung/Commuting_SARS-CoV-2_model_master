library(ggplot2)
library(reshape2)
library(viridis)

Data = read.csv("E:/R Studio/Book3.csv")
Names = c("AgeBand","0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+")
colnames(Data) = Names
#View(Data)

MeltData <- melt(Data)
colnames(MeltData) = c("Immunity_Level_By_March","AgeBand","Shielding_Immunity")

ggplot(MeltData, aes(x = Immunity_Level_By_March, y = AgeBand)) +
  geom_raster(aes(fill=Shielding_Immunity)) +  scale_fill_gradient(low="white", high= "#00549e", trans="pseudo_log") +
  theme(axis.text.y=element_text(size=9), plot.title=element_text(size=11)) + theme(legend.key=element_blank()) + labs(x = "", y ="Age group of individual") +   theme(panel.background = element_rect(fill='white'), panel.grid.major = element_blank(),panel.grid.minor = element_blank(),panel.border = element_blank()) + theme(strip.background = element_rect(fill="white"),  strip.text.x = element_text(size = 18.25))


  
Mat1 <- read.csv("E:/Global Model/SocialMixing/Canada/Scenario Prevent1.csv")
colnames(Mat1) <- Names
Mat2 <- melt(Mat1)
#View(Mat2)
colnames(Mat2) <- c("Age_group_of_individual","Age_group_of_contact","Contacts")
write.csv(Mat2, file = "E:/Global Model/File_Prevent.csv")


Lbs = c("0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+")

Mat3 <- read.csv("E:/Global Model/Scenarios - Copy.csv")
colnames(Mat3) <- c("Scenario","Age_group_of_individual","Age_group_of_contact","Contacts")
Mat3$Age_group_of_individual<- factor(Mat3$Age_group_of_individual, levels = Lbs)
Mat3$Age_group_of_contact<- factor(Mat3$Age_group_of_contact, levels = Lbs)
Matrix <- ggplot(Mat3, aes(x = Age_group_of_individual, y = Age_group_of_contact)) +
  geom_raster(aes(fill=Contacts)) +  
  scale_fill_gradient(low="white", high= "#00703c", trans="log10",limits = c(0.0015,15)) +
  theme(axis.text.y=element_text(size=9), plot.title=element_text(size=11)) + 
  theme(legend.key=element_blank()) + 
  labs(x = "Age group of individual", y ="Age group of contact") +   
  theme(panel.background = element_rect(fill='white'), panel.grid.major = element_blank(),panel.grid.minor = element_blank(),panel.border = element_blank()) + theme(strip.background = element_rect(fill="white"),  strip.text.x = element_text(size = 18.25)) + facet_wrap(~ Scenario, nrow = 1) +   scale_x_discrete(labels = Lbs)
Matrix = Matrix +  
  theme(axis.text.x=element_text(size=9, angle=45, vjust=0.3),
                         axis.text.y=element_text(size=9),
                         plot.title=element_text(size=11)) + 
  theme(legend.key=element_blank()) + coord_flip()

Matrix




Mat4 <- read.csv("E:/Global Model/Scenarios Imm.csv")
colnames(Mat4) <- c("Scenario","Age_group_of_individual","Age_group_of_contact","Effective_contacts")

Mat4$Age_group_of_individual<- factor(Mat4$Age_group_of_individual, levels = Lbs)
Mat4$Age_group_of_contact<- factor(Mat4$Age_group_of_contact, levels = Lbs)

ImmMatrix <- ggplot(Mat4, aes(x = Age_group_of_individual, y = Age_group_of_contact)) +
  geom_raster(aes(fill=Effective_contacts)) +  scale_fill_gradient(low="white", high= "#00703c", trans="log10", limits = c(0.0015,15)) +
  theme(axis.text.y=element_text(size=9), plot.title=element_text(size=11)) + theme(legend.key=element_blank()) + labs(x = "Age group of individual", y ="Age group of contact") +   theme(panel.background = element_rect(fill='white'), panel.grid.major = element_blank(),panel.grid.minor = element_blank(),panel.border = element_blank()) + theme(strip.background = element_rect(fill="white"),  strip.text.x = element_text(size = 18.25)) + facet_wrap(~ Scenario, nrow = 1)
ImmMatrix = ImmMatrix +  theme(axis.text.x=element_text(size=9, angle=45, vjust=0.3),
                         axis.text.y=element_text(size=9),
                         plot.title=element_text(size=11)) + theme(legend.key=element_blank()) + coord_flip()
ImmMatrix



FamilyMat <- read.csv("E:/Global Model/SocialMixing/CA home SocialMixing.csv")
colnames(FamilyMat) <- c("AgeBand","0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+")
FamilyMat <- melt(FamilyMat)
colnames(FamilyMat) <- c("Age_group_of_individual","Age_group_of_contact","Effective_contacts")

FamilyMat$Age_group_of_individual<- factor(FamilyMat$Age_group_of_individual, levels = Lbs)
FamilyMat$Age_group_of_contact<- factor(FamilyMat$Age_group_of_contact, levels = Lbs)

FamilyMatrix <- ggplot(FamilyMat, aes(x = Age_group_of_individual, y = Age_group_of_contact)) +
  geom_raster(aes(fill=Effective_contacts)) +  scale_fill_gradient(low="white", high= "#00703c", trans="log10", limits = c(0.0015,1.5)) +
  theme(axis.text.y=element_text(size=9), plot.title=element_text(size=11)) + theme(legend.key=element_blank()) + labs(x = "Age group of individual", y ="Age group of contact") +   theme(panel.background = element_rect(fill='white'), panel.grid.major = element_blank(),panel.grid.minor = element_blank(),panel.border = element_blank()) + theme(strip.background = element_rect(fill="white"),  strip.text.x = element_text(size = 18.25))
FamilyMatrix = FamilyMatrix +  theme(axis.text.x=element_text(size=9, angle=45, vjust=0.3),
                               axis.text.y=element_text(size=9),
                               plot.title=element_text(size=11)) + theme(legend.key=element_blank()) + coord_flip()
FamilyMatrix

