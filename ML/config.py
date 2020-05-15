import argparse

# 인자 값을 받을 수 있는 인스턴스 생성
parser = argparse.ArgumentParser(description='')

# 입력받을 인자값 등록

# 학습시킬 이미지 폴더 경로
parser.add_argument('--path', type=str)
# epoch 횟수
parser.add_argument('--epochs', type=int, default=1)
# batch size
parser.add_argument('--batchsize', type=int, default=256)

config = parser.parse_args()
