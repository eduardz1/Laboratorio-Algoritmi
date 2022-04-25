library(ggplot2)
library(Rmisc)
library(data.table)
fp <- data.frame(fread(
       file = "time_log_qsort.csv",
       sep = ";", header = T, na.strings = "NA"
))

run_20kk <- data.frame(fp[fp$size == 20000000, ])

boxplot(run_20kk$MEDIAN3,
       run_20kk$RANDOM,
       run_20kk$FIRST,
       run_20kk$MIDDLE,
       run_20kk$LAST,
       horizontal = FALSE,
       outline = FALSE,
       names = c("MEDIAN3", "RANDOM", "FIRST", "MIDDLE", "LAST")
)

summary(run_20kk$MEDIAN3)
################################################################################
################################################################################
pivot <- data.frame(fp[fp$size != 20000000, ])
pivot <- na.omit(pivot)
## Exponential graph ##
plot(pivot$size,
       pivot$FIRST,
       type = "l",
       col = rgb(red = 1, green = 0, blue = 0, alpha = 1)
)
lines(pivot$size,
       pivot$RANDOM,
       col = rgb(red = 0, green = 0, blue = 1, alpha = 1)
)
lines(pivot$size,
       pivot$MEDIAN3,
       col = rgb(red = 0, green = 1, blue = 0, alpha = 1)
)
lines(pivot$size,
       pivot$MIDDLE,
       col = rgb(red = 0.5, green = 0, blue = 0.5, alpha = 1)
)
lines(pivot$size,
       pivot$LAST,
       col = rgb(red = 0.5, green = 0.5, blue = 0, alpha = 1)
)
## Zoom in on the pivot that don't degenerate in O(n^2) ##
pivot <- data.frame(fp[fp$size != 20000000, ])
pivot <- pivot[, c("MEDIAN3", "MIDDLE", "RANDOM", "size")];

ggplot(pivot, aes(pivot$size, pivot$MEDIAN3)) +
       geom_smooth(aes(pivot$size, pivot$MEDIAN3, group = 1),
              method = "loess", color = "#ff6c29"
       ) +
       geom_smooth(aes(pivot$size, pivot$MIDDLE, group = 1),
              method = "loess", color = "#84da33"
       ) +
       geom_smooth(aes(pivot$size, pivot$RANDOM, group = 1),
              method = "loess", color = "#2558ff"
       )
