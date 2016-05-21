__author__ = 'joy'
import numpy as np
from scipy.sparse import csr_matrix
from sklearn import cluster
import sys

# method for load csr
def load_sparse_csr(filename):
    loader = np.load(filename)
    return csr_matrix((  loader['data'], loader['indices'], loader['indptr']),
                         shape = loader['shape'])

if __name__=='__main__':
    # load csr
    X=load_sparse_csr(sys.argv[2])

    # KMeans no random
    k = int(sys.argv[1])
    print(k.__str__()+" KMeans clustering...")
    model = cluster.KMeans(n_clusters=k)
    model.fit(X)

    # write to file
    f= open(sys.argv[3], 'w')
    for term, clusterid in enumerate(model.labels_):
        f.write('{0},{1}\n'.format(term, clusterid))
    f.close()
