data.frame(time_log_skiplist)

plot(time_log_skiplist$MAX_HEIGHT, time_log_skiplist$INSERT, col = "blue")
points(time_log_skiplist$MAX_LEVEL, time_log_skiplist$INSERT, col = "red")

plot(time_log_skiplist$MAX_HEIGHT, time_log_skiplist$INSERT, xlim = c(15,39), ylim = c(0.96, 1.04), col = "blue")
points(time_log_skiplist$MAX_LEVEL, time_log_skiplist$INSERT, xlim = c(15,39), ylim = c(0.96, 1.04), col = "red")

plot(time_log_skiplist$MAX_HEIGHT, time_log_skiplist$SEARCH, col = "blue")
points(time_log_skiplist$MAX_LEVEL, time_log_skiplist$SEARCH, col = "red")

plot(time_log_skiplist$MAX_HEIGHT, time_log_skiplist$SEARCH, xlim = c(15,39), ylim = c(5e-05, 2e-04), col = "blue")
points(time_log_skiplist$MAX_LEVEL, time_log_skiplist$SEARCH, xlim = c(15,39), ylim = c(5e-05, 2e-04), col = "red")


getmode <- function(v) {
  uniqv <- unique(v)
  uniqv[which.max(tabulate(match(v, uniqv)))]
}

getmode(time_log_skiplist$MAX_LEVEL) # 20
summary(time_log_skiplist)