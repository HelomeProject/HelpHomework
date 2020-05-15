from keras.models import load_model
from keras.preprocessing.image import ImageDataGenerator
from keras import models
from keras.layers import Conv2D, MaxPooling2D, Flatten, Dense,Dropout
import pickle
import numpy as np
import tensorflow as tf
# from model import vggtest

# class CNN(models.Sequential):
#     def __init__(self, input_shape, num_classes):
#         super().__init__()

#         self.add(Conv2D(32, kernel_size=(3, 3), activation='relu', input_shape=input_shape))
#         self.add(Conv2D(64, (3, 3), activation='relu'))
#         self.add(MaxPooling2D(pool_size=(2, 2)))
#         self.add(Dropout(0.25))
#         self.add(Flatten())
#         self.add(Dense(64, activation='relu'))
#         self.add(Dropout(0.5))
#         self.add(Dense(num_classes, activation='softmax'))

#         self.compile(loss='categorical_crossentropy',  # mean square error, mean absolute error 등 사용 가능
#                     optimizer='adam',                 # 최적화 알고리즘, sgd, adam 등등
#                     metrics=['accuracy'])


def load_image(image_path):
    img = tf.io.read_file(image_path)
    img = tf.image.decode_jpeg(img, channels=3)
    img = tf.image.resize(img, (45, 45))
    img = tf.reshape(img, (-1, 45, 45, 1))

    return img


with open('classes.pkl', 'rb') as f:
    classes = pickle.load(f)

# from model import CNN
# cnn = CNN((45, 45, 3), 17)
new_model = load_model('test.h5') #, custom_objects={'CNN': cnn}


# PATH = "./data"

# IMG_SIZE = new_model.input_shape[1:3]

testimage = "238.jpg"
testimage = load_image(testimage)

print('shape: ', testimage.shape)
output = new_model.predict(testimage)
print(output)
np.set_printoptions(formatter={'float': lambda x: "{0:0.3f}".format(x)})
print(classes[np.argmax(output)])
print(output)
