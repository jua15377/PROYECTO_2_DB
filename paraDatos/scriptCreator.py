from random import randint
from random import choice
from random import uniform
import names
ultimaLlave = 2

listBanco = list(range(1,10))
listDepto = list(range(1,23))
listSucursal = list(range(1,11))
listCategoria = list(range(1,17))
listOcupacion = list(range(1,10))

#INSERT INTO cliente VALUES (1, 'Diego','Calderon','5/6/1996', null,'','', 12, 4, 2, 3, 16 , 150.85,false, 0,null)
#                           (2,'Beverly','Mcgloster','1954-1-22',null,'', '', 17, 2,7, 16,7013.03,true,4332.19,null),
f = open('clients.sql', 'w')

cadena = ''
f.write("INSERT INTO cliente VALUES\n")

def getFecha(A_inicio, A_fin):

    mes = randint(1,12)
    anio = randint(A_inicio,A_fin)
    if (mes == 2):
        dia = randint(1,29)
    elif (mes == 1 or 3 or 5 or 7 or 8 or 10 or 12 ):
        dia = randint(1,31)
    else:
        dia = randint(1,30)

    return str(anio) + "-" + str(mes) + "-" + str(dia)


while ultimaLlave < 101:
    idcliente = ultimaLlave

    sexo = randint(0,1)
    if (sexo == 1):
        nombre = names.get_first_name("male")
    else:
        nombre = names.get_first_name("female")
    apellido = names.get_last_name()
    fecchacumple = getFecha(1955, 2003)
    banco = choice(listBanco)
    depto = choice(listDepto)
    sucursal = choice(listSucursal)
    categoria = choice(listCategoria)
    ocupacion = choice(listOcupacion)
    ultCompra = round(uniform(0,randint(1,20000)),2)
    #para deuda

    if (randint(0,1) == 1):
        deuda = 'true'
        cantCreditos = round(uniform(0,randint(1,20000)),2)
    else:
        deuda = 'false'
        cantCreditos = 0



    #escribir la lines
    linea = '(' + str(idcliente) + ',\'' + nombre + '\',\'' + apellido + '\',\'' + fecchacumple + '\', null,\'\',\'\',' +\
            str(depto) +',' +str(ocupacion) + ',' + str(banco) +',' + str(sucursal) +',' + str(categoria) + ',' + str(ultCompra) +',' + \
            deuda +',' + str(cantCreditos) +  ',null),\n'

    if ultimaLlave == 100:
        linea = '(' + str(
            idcliente) + ',\'' + nombre + '\',\'' + apellido + '\',\'' + fecchacumple + '\', null,\'\',\'\',' + \
                str(depto) + ',' + str(ocupacion) + ',' + str(banco) + ',' + str(sucursal) + ',' + str(
            categoria) + ',' + str(ultCompra) + ',' + \
                deuda + ',' + str(cantCreditos) + ',null);\n'
    print(linea)
    f.write(linea)
    ultimaLlave = ultimaLlave + 1