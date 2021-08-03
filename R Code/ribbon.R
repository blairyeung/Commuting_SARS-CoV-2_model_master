library(ggplot2)

fun.1 <- function(x) (1/factorial(0.5)) * (1/22) * (x/22)**0.5 * ((exp(1))** (-x/22))

x = c(1:1501)
y = c(1:1501)

for(i in c(0:1500)){
  print(fun.1(i))
  x[i+1] = i * 0.1
  y[i+1] = fun.1(i*0.1)
}

result = cbind(x,y)

result <- as.data.frame(result)

p <- ggplot(data = data.frame(x = 0), mapping = aes(x = x))

p <- ggplot(result, x = x) + 
  geom_ribbon(data=subset(result ,x>0 & x<60), aes(x = x, ymax = y, ymin = 0, alpha = I(0.4)), fill = "#b30838") +
  geom_ribbon(data=subset(result ,x>60 & x<75), aes(x = x, ymax = y, ymin = 0, alpha = I(0.4)), fill = "#ee2e24") +
  geom_ribbon(data=subset(result ,x>75 & x<90), aes(x = x, ymax = y, ymin = 0, alpha = I(0.4)), fill = "#ffad59") +
  geom_ribbon(data=subset(result ,x>90 & x<120), aes(x = x, ymax = y, ymin = 0, alpha = I(0.4)), fill = "#e0c000") +
  geom_ribbon(data=subset(result ,x>120), aes(x = x, ymax = y, ymin = 0, alpha = I(0.4)), fill = "#91bda6") +
  geom_vline(linetype = "dashed", linetype = "dashed", xintercept = 60, color = "#b30838") + geom_vline(linetype = "dashed", xintercept = 75, color = "#ee2e24") + 
  geom_vline(linetype = "dashed", xintercept = 90, color = "#ffad59") + geom_vline(linetype = "dashed", xintercept = 120, color = "#e0c000") +
  geom_line(aes(x = x, y = y, size = I(1))) +
  ylab(label = "Frequency") + xlab(label = "Commute_time") +  scale_x_continuous(limits = c(0,150), breaks = scales::pretty_breaks(n=20)) + ylim(0, 0.025)

p


