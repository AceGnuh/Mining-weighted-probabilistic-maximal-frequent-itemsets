
# Weighted probabilistic maximal frequent itemset (wPMFI_Apriori)

# Source code:

## How to run project ?

- Di chuyển tới thư mục chứa code

### Compile code
- javac -d bin -cp lib/* wPFI/entities/\*.java wPFI/supports/\*.java wPFI/utils/\*.java wPFI/algorithms/\*.java wPFI/functions/\*.java wPFI/test/\*.java Main.java

### Run
- java -cp ".;lib/commons-math3-3.6.1.jar;bin;" Main algorithms nameDataset minSupport minConfidence

- java -cp ".;lib/commons-math3-3.6.1.jar;bin;" Main connect4 0.3 0.9 (sử dụng thuật toán WPMFIM, dataset: connect4, min support: 0.3, min confidence: 0.9)

#### Giải thích tham số

- nameDataset: tên bộ dữ liệu (T40I10D100K, connect4, accidents, US)

- minSupport: độ hỗ trợ tối thiểu

- minConfidence: độ tin cậy tối thiểu
