library(ggplot2)
library(ggmap)
library(RColorBrewer)
library(sf)
library(rnaturalearth)
library(rnaturalearthdata)
library(rnaturalearthhires)
library(rgeos)


-if(!requireNamespace("devtools")) install.packages("devtools")
devtools::install_github("dkahle/ggmap", ref = "tidyup")

register_google(key="AIzaSyCT4fDuCbJPXQ-vG-2Uplk5CpHvIPFqnlQ")

MapT <- ggmap(map)

Data <- read.csv("E:/Global Model/Urban.csv")
colnames(Data) <- c("name","longt","lat","population")


map<-get_map(location='Ontario',maptype='roadmap',zoom=5)
ggmap(map)

Rast <- ggmap(map) +   stat_density2d_filled(data = Data ,aes(x = longt, y = lat, alpha = I(0.3)), bins = 4, contour_var = "ndensity") + scale_fill_brewer(palette = "YlOrRd")

Rast

g <- ggplot() +
  geom_density2d_filled(data = Data ,aes(x = longt, y = lat, alpha = I(0.9)), contour_var = "ndensity", bins = 4, geom = "polygon") + scale_fill_brewer(palette = "YlOrRd") + 
g

#display.brewer.all(type="seq")



Torontomap<-get_map(location='Toronto',maptype='roadmap',zoom=7)
ggmap(Torontomap)

OntarioMap<-get_map(location='Toronto',maptype='roadmap',zoom=5)
ggmap(OntarioMap)


Rast <- ggmap(Torontomap) +   stat_density2d_filled(data = Data ,aes(x = longt, y = lat, alpha = I(0.3)), bins = 4, geom = "polygon",contour_var = "ndensity") + scale_fill_brewer(palette = "YlOrRd")

Rast

#BubblePoint

S <- (ggmap(OntarioMap) + coord_fixed(ylim=c(40,50),xlim = c(-92,-72), ratio=1/cos(pi*45/180))) +
  geom_point(data = Data, aes(x = longt, y=lat, size = (as.numeric(population)), color = population, alpha = I(0.5))) + scale_size(range = c(2,20), name = "Size") + scale_color_gradient(low = "#e0c000", high = "#b30838")
S

  #ggplot(Data,aes(x = longt, y=lat, size = (as.numeric(population)), color = population)) + 
  #geom_point(aes(alpha = I(0.6))) + scale_size(range = c(2,20), name = "Size") + scale_color_gradient(low = "#e0c000", high = "#b30838")
#+  scale_size(name = population, range = c(0,1000000))

state_prov <- rnaturalearth::ne_states(("canada"))
