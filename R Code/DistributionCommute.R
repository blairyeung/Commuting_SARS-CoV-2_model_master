library(ggplot2)
library(reshape2)
library(sc)

Distribution_transformation <- read.csv('K:/New folder/Commuting distance distribution.csv')

colnames(Distribution_transformation) <- c('Distance','Original','Smooth_1','Smooth_2')

MeltedTransformation <- melt(Distribution_transformation)

Arr <- c(1:24)

for (i in c(1:24)) {
  Arr[i] = i * 5;
}

MeltedTransformation$Distance <- Arr

colors <- c("#850c70", "#936fb1", "#009fc2")

colnames(MeltedTransformation) <- c('Distance','Smoothing','Frequency')

exp.model <-lm(y ~ exp(x), df)

p <- ggplot(data = MeltedTransformation,aes(x = Distance, y = Frequency)) +
  geom_ribbon(data=subset(MeltedTransformation, Distance>=0 & Distance<=15), aes( ymax = 0.5, ymin = 0, alpha = I(0.5)), fill = "grey") +
  geom_bar(stat = 'identity', position=position_dodge(), aes(fill = Smoothing))+
  scale_fill_manual(values = colors) +
  scale_color_manual(values = colors) +
  geom_smooth(method = "nls", formula = y~a*exp(b*x), se = FALSE)

p

