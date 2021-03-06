from keras.models import Sequential
from keras.layers import Dense, Activation
from keras.layers import Conv2D, MaxPooling2D, BatchNormalization, Flatten, Dropout
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from keras.callbacks import ModelCheckpoint

epochs = 14
batch_size = 256
nb_classes = 17
img_size = (45,45) # pixel size

image_path = './DataPath/'

model = Sequential()

model.add(Conv2D(64, (3, 3), activation='relu', input_shape=(45, 45, 1)))  # Number of filters , Filter size, Activation function , input_shape=(pixel size, Dimension)
model.add(Conv2D(128, (3, 3), activation='relu'))
# model.add(Conv2D(32, (3, 3), activation='relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))
model.add(Dropout(0.25))
# model.add(BatchNormalization())
model.add(Flatten())
model.add(Dense(128, activation='relu'))
model.add(Dropout(0.5))
model.add(Dense(nb_classes, activation='softmax'))

model.compile(loss='categorical_crossentropy',    # mean square error, mean absolute error 등 사용 가능
              optimizer='adam',                   # 최적화 알고리즘, sgd, adam 등등
              metrics=['accuracy'])

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

classes = train_gen.class_indices
import pickle
with open('classes.pkl', 'wb') as f:
    pickle.dump(classes, f)

checkpoint = ModelCheckpoint(filepath='model.hdf5', # File name to save the learned
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