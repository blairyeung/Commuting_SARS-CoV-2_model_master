f = read.csv("E:/Global Model/Vaccine/County Output/10 to 14FullFileCMat-ON Trail Matverage.csv")

s = read.csv("E:/Regional Mobility/Canada Regional Mobility/Ontario_Mobility_Report.csv")


ibrary(esquisse)
library(ggplot2)
library(dplyr)
Work = f$Work

data.frame(f)

d = c(1:450)

function(x)

scale_color_manual(values = c("#b30838","#ee2e24","#ffad59","#e0c000","#91bda6"))
cs = c("#b30838","#ee2e24","#ffad59","#e0c000","#91bda6","#6cb33f","#00703c","#009fc2","#00549e","#936fb1","#850c70")
  
ggplot() +
  geom_line(data = f, aes(x = d, y = X15_to_19_Exposed), color = cs[1], size = 1) + 
  geom_line(data = f, aes(x = d, y = X25_to_29_Exposed), color = cs[2], size = 1)


ggplot() + 
  geom_line(data = f, aes(x = d, y = X0_to_4_Exposed+X5_to_9_Exposed),color = cs[1], size = 1) + 
  geom_line(data = f, aes(x = d, y = X10_to_14_Exposed+X15_to_19_Exposed),color = cs[2], size = 1) + 
  geom_line(data = f, aes(x = d, y = X20_to_24_Exposed+X25_to_29_Exposed),color = cs[3], size = 1) + 
  geom_line(data = f, aes(x = d, y = X30_to_34_Exposed+X35_to_39_Exposed),color = cs[4], size = 1) + 
  geom_line(data = f, aes(x = d, y = X40_to_44_Exposed+X45_to_49_Exposed),color = cs[5], size = 1) + 
  geom_line(data = f, aes(x = d, y = X50_to_54_Exposed+X55_to_59_Exposed),color = cs[6], size = 1) + 
  geom_line(data = f, aes(x = d, y = X60_to_64_Exposed+X65_to_69_Exposed),color = cs[7], size = 1) + 
  geom_line(data = f, aes(x = d, y = X70_to_74_Exposed+X75._Exposed),color = cs[8], size = 1) +
  ylim(10,10000) + scale_y_log10(limits = c(50,10000)) + labs(x = "Day", y = "Cases") + xlim(c(50,450))
  
List = c((f$X0_to_4_Exposed+f$X5_to_9_Exposed),
          (f$X10_to_14_Exposed+f$X15_to_19_Exposed),
          (f$X20_to_24_Exposed+f$X25_to_29_Exposed),
          (f$X30_to_34_Exposed+f$X35_to_39_Exposed),
          (f$X40_to_44_Exposed+f$X45_to_49_Exposed),
          (f$X50_to_54_Exposed+f$X55_to_59_Exposed),
          (f$X60_to_64_Exposed+f$X65_to_69_Exposed),
          (f$X70_to_74_Exposed+f$X75._Exposed));

library(tidyr)

f %>%
gather(f$X0_to_4_Exposed,f$X5_to_9_Exposed,
       f$X10_to_14_Exposed,f$X15_to_19_Exposed,
       f$X20_to_24_Exposed,f$X25_to_29_Exposed,
       f$X30_to_34_Exposed,f$X35_to_39_Exposed,
       f$X40_to_44_Exposed,f$X45_to_49_Exposed,
       f$X50_to_54_Exposed,f$X55_to_59_Exposed,
       f$X60_to_64_Exposed,f$X65_to_69_Exposed,
       f$X70_to_74_Exposed,f$X75._Exposed,
       key = "var", value = "value")




ggplot() + 
  geom_line(data = f, aes(x = d, y = X0_to_4_Exposed+X5_to_9_Exposed), color = cs[1], size = 1) + 
  geom_line(data = f, aes(x = d, y = X10_to_14_Exposed+X15_to_19_Exposed), color = cs[2], size = 1) + 
  geom_line(data = f, aes(x = d, y = X20_to_24_Exposed+X25_to_29_Exposed), color = cs[4], size = 1) + 
  geom_line(data = f, aes(x = d, y = X30_to_34_Exposed+X35_to_39_Exposed), color = cs[5], size = 1) + 
  geom_line(data = f, aes(x = d, y = X40_to_44_Exposed+X45_to_49_Exposed), color = cs[7], size = 1) + 
  geom_line(data = f, aes(x = d, y = X50_to_54_Exposed+X55_to_59_Exposed), color = cs[8], size = 1) + 
  geom_line(data = f, aes(x = d, y = X60_to_64_Exposed+X65_to_69_Exposed), color = cs[9], size = 1) + 
  geom_line(data = f, aes(x = d, y = X70_to_74_Exposed+X75._Exposed), color = cs[11], size = 1) + 
  scale_y_log10(limits = c(50,10000))
  
library(ggplot2)

qplot(sub_region_1, workplaces_percent_change_from_baseline, data = e, geom = "jitter", alpha = I(0.02), color = sub_region_1)

e = read.csv("E:/Regional Mobility/Canada Regional Mobility/Ontario_March_Facets.csv")

library(ggplot2)

median(e$workplaces_percent_change_from_baseline)

ggplot(data = e,
       aes(x = Code, y = value), fill = Code) +
  geom_violin(size = 0.72) +
  geom_jitter(alpha = I(0.5), aes(color = Code))  + labs(x = "Restriction level") + scale_x_discrete(labels = c("Prevent","Protect","Restrict","Control","Lockdown")) + labs(color = "Level of Restriction", y = "Percentage_change_from_baseline") + facet_wrap(~ Category) + theme(axis.text.x=element_text(angle=45, hjust=1))

geom_label(data = e, aes(x = Code, y = -15.89041096, label = "-15.89041096"),  size = 3, vjust = 0)

                                                                                                                                                                                                                                                                                                        
num <- (e$value)
median(as.numeric(num), na.rm=TRUE)


library(ggcorrplot)

Names = c("0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+")

ggcorrplot(Immmat,
           outline.color = "white",
           colors = c("#6D9EC1", "white", "#E46726")) + scale_x_discrete(labels = Names) 

Mat = matrix(c(0.738939293,0.887634514,0.946962879,0.600656516,0.697623162,0.725042712,0.97669627,1,1,1,1,0.851095239,0.938336353,0.900901411,0.637894137,0.46226507,0.893978119,0.954990174,0.957544726,0.948258935,0.888046587,0.895444738,0.840097126,0.845129528,0.868873294,0.887298953,0.959678157,0.881512341,0.846209719,0.846368583,0.815180459,0.835297785), byrow = T, nrow = 2, ncol =16)

colnames(Mat) <- Names
rownames(Mat) <- c("0 to 4 Prior", "75+ Prior")

print(Mat)


library(reshape2)
longData<-melt(Mat)
longData<-longData[longData$value!=0,]

MatrixTest <- ggplot(longData, aes(x = Var2, y = Var1)) + 
  geom_raster(aes(fill=value)) + 
  scale_fill_gradient(low="grey90", high= cs[7], limit = c(0,1)) +
  labs(x="Age Band i", y="Prioritzed age band for vaccination", title="Immunity Matrix") +
  theme_bw() + theme(axis.text.x=element_text(size=9, angle=45, vjust=0.3),
                     axis.text.y=element_text(size=9),
                     plot.title=element_text(size=11))
MatrixTest

data(e)
