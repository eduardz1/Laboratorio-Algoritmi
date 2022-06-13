library(data.table)
library(tikzDevice)
library(ggplot2)
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
tikz("latex/figures/sklist_insert.tex", width = 5, height = 2)

ggplot(log, aes(50)) + 
       geom_point(aes(y = INSERT, x = MAX_HEIGHT, color = "Reachable"), shape = 1, alpha = 0.5, show.legend = TRUE) + 
       geom_point(aes(y = INSERT, x = MAX_LEVEL, color = "Reached"), shape = 1, alpha = 0.5, show.legend = TRUE) + 
       ylab("time (s)") + xlab("levels") + theme_bw()

dev.off()
## Zoom in on insert ##
tikz("latex/figures/sklist_zoommed_insert.tex", width = 5, height = 2)

ggplot(log, aes(50)) + 
       geom_point(aes(y = INSERT, x = MAX_HEIGHT, color = "Reachable"), shape = 1, alpha = 0.5) + 
       geom_point(aes(y = INSERT, x = MAX_LEVEL, color = "Reached"), shape = 1, alpha = 0.5) + 
       ylab("time (s)") + xlab("levels") + theme_bw() + ylim(0.96, 1.04) + xlim(10, 40)

dev.off()
## Mean time of insert ##
tikz("latex/figures/sklist_mean_insert.tex", width = 5, height = 2)

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
df <- data.frame(levels, mean_time_insert)
ggplot(df, aes(x = levels, y = mean_time_insert)) +
       geom_point() + ylab("time (s)") + xlab("levels") + theme_bw() + scale_x_continuous(breaks = levels)
dev.off()
################################################################################
## Search ######################################################################
tikz("latex/figures/sklist_search.tex", width = 5, height = 2)

ggplot(log, aes(50)) + 
       geom_point(aes(y = SEARCH, x = MAX_HEIGHT, color = "Reachable"), shape = 1, alpha = 0.5, show.legend = TRUE) + 
       geom_point(aes(y = SEARCH, x = MAX_LEVEL, color = "Reached"), shape = 1, alpha = 0.5, show.legend = TRUE) + 
       ylab("time (s)") + xlab("levels") + theme_bw()

dev.off()
## Zoom in on search ##
tikz("latex/figures/sklist_zoommed_search.tex", width = 5, height = 2)
ggplot(log, aes(50)) + 
       geom_point(aes(y = SEARCH, x = MAX_HEIGHT, color = "Reachable"), shape = 1, alpha = 0.5) + 
       geom_point(aes(y = SEARCH, x = MAX_LEVEL, color = "Reached"), shape = 1, alpha = 0.5) + 
       ylab("time (s)") + xlab("levels") + theme_bw() + ylim(5e-05, 2e-04) + xlim(10, 40)

dev.off()
## Mean time of search ##
tikz("latex/figures/sklist_mean_search.tex", width = 5, height = 2)

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
ggplot(df, aes(x = levels, y = mean_time_search)) +
       geom_point() + ylab("time (s)") + xlab("levels") + theme_bw() + scale_x_continuous(breaks = levels)
dev.off()
################################################################################
