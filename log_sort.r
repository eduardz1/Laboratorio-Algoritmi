data.frame(time_log_qsort)

boxplot(time_log_qsort$MEDIAN3,
        time_log_qsort$RANDOM,
        time_log_qsort$FIRST,
        time_log_qsort$MIDDLE,
        time_log_qsort$LAST,
        horizontal = FALSE, outline = FALSE, names = c("MEDIAN3", "RANDOM", "FIRST", "MIDDLE", "LAST"))

summary(time_log_qsort$MEDIAN3)