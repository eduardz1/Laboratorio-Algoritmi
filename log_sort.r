library(ggplot2)
library(Rmisc)
library(data.table)
library(tikzDevice)
fp <- data.frame(fread(
       file = "time_log_qsort2.csv",
       sep = ";", header = T, na.strings = "NA"
))
tikz("latex/figures/qs_boxplot_pivot.tex", width = 5, height = 3)

# section only containing run on 20 million shuffled records
main <- data.frame(pivot = fp[fp$size == 20000000 & fp$file == "es1_dataset/records.csv", ]$pivot,
                   time = fp[fp$size == 20000000 & fp$file == "es1_dataset/records.csv", ]$time)

ggplot(main, aes(x = pivot, y = time)) +
       geom_boxplot() +
       ylab("time (s)") +
       theme_bw()
dev.off()
summary(main$time[main$pivot == "MEDIAN3"])
################################################################################
## SORTED ######################################################################
tikz("latex/figures/qs_plot_pivot.tex", width = 5, height = 3)

pivot <- data.frame(time = fp[fp$file == "es1_dataset/sorted.csv", ]$time,
                    pivot = fp[fp$file == "es1_dataset/sorted.csv", ]$pivot,
                    size = fp[fp$file == "es1_dataset/sorted.csv", ]$size)

ggplot(pivot, aes(x = size, y = time, color = pivot)) + 
       geom_smooth(method = loess, se = FALSE) + ylab("time (s)") + theme_bw()
dev.off()
## Zoom in on the pivot that don't degenerate in O(n^2) ##
tikz("latex/figures/qs_plot_zoommed_pivot.tex", width = 5, height = 3)
pivot_sec <- pivot[pivot$pivot == "MEDIAN3" | 
                   pivot$pivot == "RANDOM" | 
                   pivot$pivot == "MIDDLE", ]

ggplot(pivot_sec, aes(x = size, y = time, color = pivot)) + 
       geom_smooth(method = loess, se = FALSE) + ylab("time (s)") + theme_bw()
dev.off()
################################################################################
## BINARY INSERTION SORT #######################################################
tikz("latex/figures/bi_plot_unsorted.tex", width = 5, height = 3)
fpp <- data.frame(fread(
       file = "time_log_insertsort.csv",
       sep = ";", header = T, na.strings = "NA"
))
bissort <- data.frame(time = fpp[fpp$file == "es1_dataset/records.csv", ]$time,
                      size = fpp[fpp$file == "es1_dataset/records.csv", ]$size)

ggplot(bissort, aes(x = size, y = time)) + 
       geom_smooth(method = loess, se = FALSE) + ylab("time (s)") + theme_bw()
dev.off()
