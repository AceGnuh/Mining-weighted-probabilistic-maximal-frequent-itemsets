
# Weighted probabilistic maximal frequent itemset (WPMFIM)

# Source code:

## How to run project ?

- Di chuyển tới thư mục chứa code

### Compile code
- javac -d bin -cp lib/* pmfi/entities/\*.java pmfi/supports/\*.java pmfi/utils/\*.java pmfi/algorithms/\*.java pmfi/functions/\*.java test/\*.java Main.java

### Run
- java -cp ".;lib/commons-math3-3.6.1.jar;bin;" Main algorithms nameDataset minSupport minConfidence

- java -cp ".;lib/commons-math3-3.6.1.jar;bin;" Main 1 connect4 0.3 0.9 (sử dụng thuật toán WPMFIM, dataset: connect4, min support: 0.3, min confidence: 0.9)

#### Giải thích tham số

- algorithms: [WPMFIM: 1, AWPMFIM: 2]

- nameDataset: tên bộ dữ liệu (T40I10D100K, connect4, accidents, US)

- minSupport: độ hỗ trợ tối thiểu

- minConfidence: độ tin cậy tối thiểu
