from tensorflow.keras.models import load_model # PIL 과 같음.
from tensorflow.keras.preprocessing import image
import pickle
import numpy as np
import tensorflow as tf
from PIL import Image
import matplotlib.pyplot as plt
import cv2


def fillSquare(img):
    w, h = img.shape
    back = img[0][0]
    
    fill = abs(w - h)
    
    if w > h:
        li = [[back for i in range(fill // 2)] for j in range(w)]
        li = np.array(li, dtype='uint8')
        square = np.concatenate((li, img, li), axis=1)
    else:
        li = [[back for i in range(h)] for j in range(fill // 2)]
        li = np.array(li, dtype='uint8')
        square = np.concatenate((li, img, li), axis=0)

    return square

def nomalization(img_arr):
    global size
    s = 0
    cnt = size * size
    maxV = 0
    for img in img_arr:
        for i in img:
            s += i
            maxV = max(maxV,i)
    avg = s / cnt
    for img in img_arr:
        for i in range(size):
            if img[i] < avg:
                img[i] = 0
            # elif img[i] >= maxV-(10/255):
            #     img[i] = maxV
            # elif img[i] >= maxV-(20/255):
            #     img[i] = maxV-(1/255)
    
    return img_arr

def load_image(image_path):
    global size

    img = cv2.imread(image_path)
    # 컬러 -> 그레이 변환
    img_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    # 가우시안블러
    img_blur = cv2.GaussianBlur(img_gray, (5, 5), 0)

    # blur_arr = img_blur
    # s = 0
    # cnt = len(blur_arr) * len(blur_arr[0])
    # for img in blur_arr:
    #     for i in img:
    #         s += i
    # avg = s / cnt
    # print('avg: ', avg)
    # for img in blur_arr:
    #     for i in range(size):
    #         if img[i] < avg:
    #             img[i] = 230

    # img_blur = blur_arr
    # print(img_blur[0][0])
    # # threshold 기준값 정하기
    # minV = 256
    # for i in blur_arr: # img_blur
    #     minV = min(minV, min(i))
    # TH = minV + 110
    # print('threshold: ', TH)
    
    # ret, img_th = cv2.threshold(img_blur, TH, 230, cv2.THRESH_BINARY_INV) #(120,230)
    thr1 = cv2.adaptiveThreshold(img_blur, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY_INV, 3, 2)
    img_th = cv2.adaptiveThreshold(img_blur, 255, cv2.ADAPTIVE_THRESH_MEAN_C, cv2.THRESH_BINARY_INV, 3, 2)
    fig = plt.figure(figsize=(9, 6))
    img1 = thr1
    img2 = img_th
    ax1 = fig.add_subplot(1,2,1)
    ax1.imshow(img1)
    ax1.set_title('GAUSSIAN Image')
    ax2 = fig.add_subplot(1,2,2)
    ax2.imshow(img2)
    ax2.set_title('MEANC Image')
    ax1.axis('off')
    plt.show()
    
    images, contours, hierachy= cv2.findContours(thr1.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    rects = [cv2.boundingRect(each) for each in contours]

    # x값이 작은 것부터 ..!
    rects = [(x,y,w,h) for (x,y,w,h) in rects if ((w*h>100) or (w>20)) and y < 100]
    rects.sort()

    ## equ
    # last1, last2 = rects[-2], rects[-1]
    # if last1[1] < last2[1]:
    #     x_ = last1[0]
    #     y_ = last1[1]
    #     w_ = max(last1[0]+last1[2], last2[0]+last2[2]) - last1[0]
    #     h_ = last2[1]+last2[3] - last1[1] # 고정
    # else:
    #     x_ = last1[0]
    #     y_ = last2[1]
    #     w_ = max(last1[0]+last1[2], last2[0]+last2[2]) - last1[0]
    #     h_ = last1[1]+last1[3] - last2[1]
    
    # rects = rects[:-2] + [(x_, y_, w_, h_)]
    

    for rect in rects:
        cv2.rectangle(img, (rect[0]-5, rect[1]-10), (rect[0] + rect[2] + 2, rect[1] + rect[3] + 10), (0, 255, 0), 2)

    plt.imshow(img)
    plt.show()

   
    for rect in rects: # [y : y+h, x : x+w]
        new_img = img_gray[rect[1]-2 : rect[1]+rect[3]+2, rect[0] : rect[0]+rect[2]]

        # 크기조절
        # 1) fillsquare 사용
        squared_img = fillSquare(new_img)
        resize_img = cv2.resize(squared_img, (size, size))
        # median_img = cv2.medianBlur(resize_img,3)
        # filter_blur = cv2.bilateralFilter(resize_img,9,75,75)
        resize_img = cv2.GaussianBlur(resize_img, (3, 3), 0)

        # 이미지반전
        img_inv = cv2.bitwise_not(resize_img)
        
        img_arr = image.img_to_array(img_inv, dtype='float32')
        img_arr = img_arr.astype('float32') / 255

        img_arr = nomalization(img_arr)
        ii = image.array_to_img(img_arr)
        plt.imshow(ii)
        plt.show()

        pre(new_model, img_arr)
    

def define_classes():
    global size
    with open('classes_{}.pkl'.format(size), 'rb') as f:
        new_classes = pickle.load(f)
    classes = {}
    for key, value in new_classes.items():
        classes[value] = key

    return classes


def pre(new_model, img_arr):
    global classes, predict_nums, predict_res

    output = new_model.predict(img_arr[None, :, :, :])
    # output = new_model.predict_classes(img_arr)
    np.set_printoptions(formatter={'float': lambda x: "{0:0.5f}".format(x)})
    predict_num = classes[np.argmax(output)]


    if predict_num == 'minus':
        predict_num = '-'
    elif predict_num == 'plus':
        predict_num = '+'
    elif predict_num == 'times':
        predict_num = '*'
    elif predict_num == 'div':
        predict_num = '/'
    elif predict_num == 'open':
        predict_num = '('
    elif predict_num == 'close':
        predict_num = ')'
    elif predict_num == 'equ':
        predict_num = '='
    else:
        pass
    
    print('predict: {}'.format(predict_num))

    jud = False
    if jud == True:
        predict_res += predict_num
    else:
        predict_nums += predict_num
        if predict_num == '-' and predict_nums[-2] == '-':
            jud = True

    # print()
    # print('predict: ', classes[np.argmax(output)])
    # print()
    # for i in range(len(classes)):
    #     print('{}: {} %'.format(classes[i], round(output[0][i]*100,4)))
    # print()


size = 45
predict_nums = ''
predict_res = ''
# classes 정의
classes = define_classes()

# 저장된 학습 파일열기
new_model = load_model('test_452.hdf5')

# 경로 설정 및 이미지 변환
testimage = input("파일명 입력(XX.jpg): ")
load_image(testimage)

# predict_nums = predict_nums[:-2] + '='
# print('predict: ', predict_nums)

# 계산
def calculate(predict_nums):
    result = eval(predict_nums[:-1])
    return result

print('계산 결과: ', calculate(predict_nums))