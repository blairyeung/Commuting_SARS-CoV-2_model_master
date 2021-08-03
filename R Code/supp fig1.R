#For supplementary material
library(ggplot2)
library(reshape2)
library(svglite)
Data = read.csv("E:/Regional Mobility/Canada Regional Mobility/Ca.csv")
longData = melt(Data)
#longData = longData[sample(nrow(longData),20000),]


p <- ggplot(data = longData, aes(x = as.Date(date), y = value, color = variable)) +
  geom_point(alpha = I(0.2)) + scale_x_date(breaks = "2 month", date_labels = ("%Y %b")) + 
  facet_wrap(~ variable, nrow = 1) + 
  geom_line(y = 0, color = "#4d4d4d", linetype = "dashed")+
  theme(axis.text.x=element_text(size=9, angle=45, vjust=0.3),
        axis.text.y=element_text(size=9),
        plot.title=element_text(size=11))+ theme(legend.key=element_blank()) +
  labs(x = "", y = "Percentage change from baseline") + theme(legend.key=element_blank()) + guides(color=FALSE) + ylim(c(-100,200))
p <- p + theme(strip.text.x = element_text(size = 18.25), strip.background = element_rect(fill="white"))


p
#ggsave(filename = "E:/Fgs/p.pdf", scale = 1)
ggsave(filename = "E:/R Studio/DDD.pdf", width = 20, height = 4, units = c("in","in"))

