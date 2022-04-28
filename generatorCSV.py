# Script de generare al fisierelor CSV din etapa 2, proiect PAO
import random
import string

letters = string.ascii_letters

f = open('printerSeed.txt', 'w')
f.write('brand,model\n')
for _ in range(10):
    br = ''.join(random.choice(letters) for i in range(random.randint(3, 6)))
    md = ''.join(random.choice(letters) for i in range(random.randint(2, 4)))
    f.write(f'{br},{md}\n')
f.close()


f = open('switchSeed.txt', 'w')
f.write('brand,hasGigabitEthernet\n')
for _ in range(10):
    br = ''.join(random.choice(letters) for i in range(random.randint(3, 6)))
    f.write(f'{br},{random.randint(0, 1)}\n')
f.close()


f = open('graphicscardSeed.txt', 'w')
f.write('videoMemory,isForMining,price\n')
for _ in range(10):
    vid = random.randint(100,1000)
    price = random.uniform(300, 600)
    f.write(f'{vid},{random.randint(0, 1)},{round(price,2)}\n')

f.close()


f = open('networkadapterSeed.txt', 'w')
f.write('ports,price\n')
for _ in range(10):
    por = random.randint(10,26)
    price = random.uniform(100, 200)
    f.write(f'{por},{round(price,2)}\n')

f.close()
