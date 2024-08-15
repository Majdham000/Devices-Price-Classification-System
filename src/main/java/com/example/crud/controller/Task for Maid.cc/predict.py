import pickle
import sys
import numpy as np
from sklearn.preprocessing import StandardScaler

with open('model.pkl', 'rb') as f:
    model = pickle.load(f)

input_data = np.array([float(x) for x in sys.argv[1:]])

# Feature scaling
scaler = StandardScaler()
scaled_features = scaler.fit_transform(input_data)

# Create a DataFrame with the scaled features
scaled_test = pd.DataFrame(scaled_features, columns=input_data.columns)

prediction = model.predict([input_data])
print(prediction[0])