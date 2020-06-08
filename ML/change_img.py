import glob
import cv2

for i in ('0','1','2','3','4','5','6','7','8','9','open', 'close', 'plus', 'minus', 'equ', 'times', 'div'):
    path = '.\\DataPath\\{}\\'.format(i)
    for filename in glob.glob(path + '*.jpg'):
        name = filename.replace(path, "")
        img = cv2.imread(filename)
        img = cv2.resize(img, (45, 45))
        img_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        img_inv = cv2.bitwise_not(img_gray)
        img_blur = cv2.GaussianBlur(img_inv, (5, 5), 0)
        cv2.imwrite('.\\NewDataPath\\{}\\{}'.format(i, name), img_blur)