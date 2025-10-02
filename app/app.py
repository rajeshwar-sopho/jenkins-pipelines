def print_square(size):
    for _ in range(size):
        print('* ' * size)

def print_triangle(height):
    for i in range(1, height + 1):
        print('* ' * i)

def print_pyramid(height):
    for i in range(1, height + 1):
        print(' ' * (height - i) + '* ' * i)

if __name__ == "__main__":
    print("Square:")
    print_square(5)
    print("\nTriangle:")
    print_triangle(5)
    print("\nPyramid:")
    print_pyramid(5)