from keras.models import Sequential
from keras.layers import Dense, Activation
from keras.layers import Conv2D, MaxPooling2D, BatchNormalization, Flatten, Dropout
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from keras.callbacks import ModelCheckpoint

epochs = 14
batch_size = 256
nb_classes = 17
img_size = (45,45)

image_path = './data_45/'

image_gen = ImageDataGenerator(rescale=1./255, validation_split=0.2)

train_gen = image_gen.flow_from_directory(
    batch_size=batch_size,
    color_mode='grayscale',
    directory=image_path,
    target_size=img_size,
    subset='training'
)

val_gen = image_gen.flow_from_directory(
    batch_size=batch_size,
    color_mode='grayscale',
    directory=image_path,
    target_size=img_size,
    subset='validation'
)