import random

for i in range(1000):
    if i % 100 == 0:
        print(random.uniform(-90, 90), random.uniform(-180, 180), sep=', ')
    elif i % 50 == 0:
        print(random.uniform(48, 49), random.uniform(3, 4), sep=', ')
    elif i % 2 == 0:
        print(random.uniform(45, 47), random.uniform(6, 8), sep=', ')
    else:
        print(random.uniform(50, 52), random.uniform(-1, 1), sep=', ')
