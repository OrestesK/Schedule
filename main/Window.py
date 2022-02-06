from tkinter import *
import Reader as reader


class Window(Frame):

    def __init__(self, contents):
        super().__init__()

        self.initUI(contents=contents)

    def initUI(self, contents):

        self.master.title("Schedule")
        self.pack(fill=BOTH, expand=1)

        canvas = Canvas(self)

        for elem in contents:
            temp = canvas.create_text(60, (contents.index(elem) + 1) * 30, anchor=W, font=("Purisa", 8),
                                      text=elem)

        canvas.pack(fill=BOTH, expand=1)


def main():

    root = Tk()
    imported = reader.main()
    for elem in imported:
        print(elem)
    window = Window(contents=imported)

    x = (root.winfo_screenwidth()/2) - (300/2)
    y = (root.winfo_screenheight()/2) - (650/2)
    root.geometry('%dx%d+%d+%d' % (300, 650, x, y))

    root.mainloop()


if __name__ == '__main__':
    main()
