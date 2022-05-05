library(ggplot2)
library(Rmisc)
library(data.table)
library(tikzDevice)
fp <- data.frame(fread(
       file = "time_log_qsort.csv",
       sep = ";", header = T, na.strings = "NA"
))
tikz("latex/figures/qs_boxplot_pivot.tex", width = 5, height = 5)

# section only containing run on 20 million shuffled records
main <- data.frame(pivot = fp[fp$size == 20000000 &
                              fp$file == "es1_dataset/records.csv" &
                              fp$compare == "records_string", ]$pivot,
                   time = fp[fp$size == 20000000 &
                             fp$file == "es1_dataset/records.csv" &
                             fp$compare == "records_string", ]$time)

ggplot(main, aes(x = pivot, y = time, fill = pivot)) +
       geom_boxplot() +
       ylab("time (s)") +
       theme_bw() + 
       theme(legend.position = "none") +
       labs(title = "QuickSort on 20 million records",
            subtitle = "- string comparison -",
            caption = "3000 samples, 1000 for each field prioritized, 200 for every pivot.
                       The records were randomly shuffled at every run.
                       Test conducted on an intel i5-11400F CPU, 16GB RAM, Ubuntu 22.04."
            )
dev.off()
summary(main$time[main$pivot == "MEDIAN3"])
################################################################################
## SORTED ######################################################################
tikz("latex/figures/qs_plot_pivot.tex", width = 5, height = 1.5)

pivot <- data.frame(time = fp[fp$file == "es1_dataset/sorted.csv" &
                              fp$compare == "records_string", ]$time,
                    pivot = fp[fp$file == "es1_dataset/sorted.csv" &
                               fp$compare == "records_string", ]$pivot,
                    size = fp[fp$file == "es1_dataset/sorted.csv" &
                              fp$compare == "records_string", ]$size)

ggplot(pivot, aes(x = size, y = time, color = pivot)) + 
       geom_line() + 
       ylab("time (s)") + 
       theme_bw()
dev.off()
## Zoom in on the pivot that don't degenerate in O(n^2) ##
tikz("latex/figures/qs_plot_zoommed_pivot.tex", width = 5, height = 1.5)
pivot_sec <- pivot[pivot$pivot == "MEDIAN3" | 
                   pivot$pivot == "RANDOM" | 
                   pivot$pivot == "MIDDLE", ]

ggplot(pivot_sec, aes(x = size, y = time, color = pivot)) + 
       geom_line() + 
       ylab("time (s)") + 
       theme_bw()
dev.off()
################################################################################
## BINARY INSERTION SORT #######################################################
tikz("latex/figures/bi_plot_unsorted.tex", width = 5, height = 3)
fpp <- data.frame(fread(
       file = "time_log_insertsort.csv",
       sep = ";", header = T, na.strings = "NA"
))
bissort <- data.frame(time = fpp[fpp$file == "es1_dataset/records.csv" &
                                 fpp$compare == "records_string", ]$time,
                      size = fpp[fpp$file == "es1_dataset/records.csv" &
                                 fpp$compare == "records_string", ]$size,
                      binarySearch = fpp[fpp$file == "es1_dataset/records.csv" &
                                         fpp$compare == "records_string", ]$binary_search)

ggplot(bissort, aes(x = size, y = time, color = binarySearch)) +
       geom_line() +
       ylab("time (s)") +
       theme_bw() +
       labs(caption = "30000 samples for each algorithm, 10000 for each field \
                       prioritized, wiht increments of 1")
dev.off()
