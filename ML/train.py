from config import config
import pickle

from keras.preprocessing.image import ImageDataGenerator
from keras import optimizers
from keras.callbacks import ModelCheckpoint

import matplotlib.pyplot as plt

from keras.models import Sequential  
from keras.layers import Dense, Activation
from keras.layers import Conv2D, MaxPooling2D, BatchNormalization, Flatten, Dropout

from keras.applications import InceptionResNetV2
from keras.applications import VGG19
# from sklearn.model_selection import train_test_split

# PATH = config.path
PATH = "./data/"
epochs = config.epochs
batch_size = config.batchsize

InceptionV2model = InceptionResNetV2(weights=None, classes=17)

vgg19model = VGG19(weights=None, classes=17)
vggtest = VGG19(weights=None, classes=17)

num_classes = 17
IMG_SIZE = (45, 45)

model = Sequential()

model.add(Conv2D(32, kernel_size=(3, 3), activation='relu', input_shape=(45,45,3)))
model.add(Conv2D(64, (3, 3), activation='relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))
model.add(Dropout(0.25)) #model.add(BatchNormalization())
model.add(Flatten())
model.add(Dense(64, activation='relu'))
model.add(Dropout(0.5))
model.add(Dense(num_classes, activation='softmax'))

model.compile(loss='categorical_crossentropy',  # mean square error, mean absolute error 등 사용 가능
            optimizer='adam',                 # 최적화 알고리즘, sgd, adam 등등
            metrics=['accuracy'])

# dataset 준비
image_gen = ImageDataGenerator(rescale=1./255, validation_split=0.2)

train_gen = image_gen.flow_from_directory(
    batch_size=batch_size,
    directory=PATH,
    target_size=IMG_SIZE,
    subset='training'
)

val_gen = image_gen.flow_from_directory(
    batch_size=batch_size,
    directory=PATH,
    target_size=IMG_SIZE,
    subset='validation'
)

classes = train_gen.class_indices
classes = list(classes.keys())

with open('classes.pkl', 'wb') as f:
    pickle.dump(classes, f)

# X 는 이미지, Y 는 실제 값
# X_train, X_test, y_train, y_test = train_test_split(X, y, test_size= 0.2, random_state=1234)

# print(train_gen.class_indices)
# print(train_gen.classes)
# print(dir(train_gen))


checkpoint = ModelCheckpoint(filepath='test.h5',
                             monitor='loss',
                             mode='min',
                             save_best_only=True
                             )

model.summary()
history = model.fit_generator(
    train_gen,
    epochs=epochs,
    validation_data=val_gen,
    callbacks=[checkpoint]
)