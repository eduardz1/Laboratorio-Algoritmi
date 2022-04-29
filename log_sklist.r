library(data.table)
library(tikzDevice)
log <- data.frame(fread(
       file = "time_log_skiplist.csv",
       sep = ";", header = T, na.strings = "NA"
))

getmode <- function(v) {
       uniqv <- unique(v)
       uniqv[which.max(tabulate(match(v, uniqv)))]
}

getmode(log$MAX_LEVEL) # 20
summary(log)

# Insert #######################################################################
tikz("latex/sklist_insert.tex", width = 5, height = 4)

plot(log$MAX_HEIGHT,
       log$INSERT,
       col = rgb(red = 0, green = 0, blue = 1, alpha = 0.5),
       xlab = "Levels",
       ylab = "Time (s)",
       main = "Skip List Insert"
)
points(log$MAX_LEVEL,
       log$INSERT,
       col = rgb(red = 1, green = 0, blue = 0, alpha = 0.5)
)
legend(30,
       110,
       legend = c("Max level reached", "Max level reachable"),
       fill = c("red", "blue")
)
dev.off()
## Zoom in on insert ##
tikz("latex/sklist_zoommed_insert.tex", width = 5, height = 4)

plot(log$MAX_HEIGHT,
       log$INSERT,
       ylim = c(0.96, 1.04),
       col = rgb(red = 0, green = 0, blue = 1, alpha = 0.5),
       xlab = "Levels",
       ylab = "Time (s)",
       main = "Skip List Insert"
)
points(log$MAX_LEVEL,
       log$INSERT,
       ylim = c(0.96, 1.04),
       col = rgb(red = 1, green = 0, blue = 0, alpha = 0.5)
)
legend(30,
       1.035,
       legend = c("Max level reached", "Max level reachable"),
       fill = c("red", "blue")
)
dev.off()
## Mean time of insert ##
tikz("latex/sklist_mean_insert.tex", width = 5, height = 4)

levels <- c(15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
mean_time_insert <- c(
       mean(log$INSERT[log$MAX_LEVEL == 15]),
       mean(log$INSERT[log$MAX_LEVEL == 16]),
       mean(log$INSERT[log$MAX_LEVEL == 17]),
       mean(log$INSERT[log$MAX_LEVEL == 18]),
       mean(log$INSERT[log$MAX_LEVEL == 19]),
       mean(log$INSERT[log$MAX_LEVEL == 20]),
       mean(log$INSERT[log$MAX_LEVEL == 21]),
       mean(log$INSERT[log$MAX_LEVEL == 22]),
       mean(log$INSERT[log$MAX_LEVEL == 23]),
       mean(log$INSERT[log$MAX_LEVEL == 24]),
       mean(log$INSERT[log$MAX_LEVEL == 25])
)
plot(levels,
       mean_time_insert,
       pch = 16,
       cex = 2,
       col = rgb(red = 0.8, green = 0, blue = 0.1, alpha = 0.5)
)
dev.off()
################################################################################
## Search ######################################################################
tikz("latex/sklist_search.tex", width = 5, height = 4)

plot(log$MAX_HEIGHT,
       log$SEARCH,
       col = rgb(red = 0, green = 0, blue = 1, alpha = 0.5),
       xlab = "Levels",
       ylab = "Time (s)",
       main = "Skip List Search"
)
points(log$MAX_LEVEL,
       log$SEARCH,
       col = rgb(red = 1, green = 0, blue = 0, alpha = 0.5)
)
legend(30,
       0.016,
       legend = c("Max level reached", "Max level reachable"),
       fill = c("red", "blue")
)
dev.off()
## Zoom in on search ##
tikz("latex/sklist_zoommed_search.tex", width = 5, height = 4)

plot(log$MAX_HEIGHT,
       log$SEARCH,
       ylim = c(5e-05, 2e-04),
       col = rgb(red = 0, green = 0, blue = 1, alpha = 0.5),
       xlab = "Levels",
       ylab = "Time (s)",
       main = "Skip List Search"
)
points(log$MAX_LEVEL,
       log$SEARCH,
       ylim = c(5e-05, 2e-04),
       col = rgb(red = 1, green = 0, blue = 0, alpha = 0.5)
)
legend(30,
       0.00019,
       legend = c("Max level reached", "Max level reachable"),
       fill = c("red", "blue")
)
dev.off()
## Mean time of search ##
tikz("latex/sklist_mean_search.tex", width = 5, height = 4)

mean_time_search <- c(
       mean(log$SEARCH[log$MAX_LEVEL == 15]),
       mean(log$SEARCH[log$MAX_LEVEL == 16]),
       mean(log$SEARCH[log$MAX_LEVEL == 17]),
       mean(log$SEARCH[log$MAX_LEVEL == 18]),
       mean(log$SEARCH[log$MAX_LEVEL == 19]),
       mean(log$SEARCH[log$MAX_LEVEL == 20]),
       mean(log$SEARCH[log$MAX_LEVEL == 21]),
       mean(log$SEARCH[log$MAX_LEVEL == 22]),
       mean(log$SEARCH[log$MAX_LEVEL == 23]),
       mean(log$SEARCH[log$MAX_LEVEL == 24]),
       mean(log$SEARCH[log$MAX_LEVEL == 25])
)
plot(levels,
       mean_time_search,
       pch = 16,
       cex = 2,
       col = rgb(red = 0.8, green = 0, blue = 0.1, alpha = 0.5)
)
dev.off()
################################################################################