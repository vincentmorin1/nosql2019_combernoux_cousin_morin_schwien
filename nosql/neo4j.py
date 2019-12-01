import py2neo
import csv

def parseReference(references):
    ref = references.split(";")
    if ref[-1] == '':
        ref.pop()  #on pop si le dernier elts est vide
    return ref

graph = py2neo.Graph(user="neo4j", password="Yu6A9t6a") 
print("Connexion reussie")
graph.delete_all()
print("Donnees supprimees")
tx = graph.begin()

with open("./VersionUNREVIEWED.tab") as tsv:

    file = csv.reader(tsv, dialect="excel-tab")
    all_keys = next(file)
    print("Fichier ouvert, ecriture en cours...")
    n = 0
    for row in file:
        n+=1
        node = py2neo.Node("Prot")
        node["Name"] = row[3]
        node['Entry'] = row[0]
        node["id"] = n
        #node['Entry_name'] = row[1]
        #node['Gene_names'] = row[4]
        #node['Organism'] = row[5]
        #node['Length'] = row[6]
        node['Cross_reference'] = parseReference(row[7])
        #node['Sequence'] = row[8]
        #node['EC_Number'] = row[11]
        tx.create(node)

tx.commit()

print("Ecriture reussie")