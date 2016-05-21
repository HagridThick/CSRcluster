__author__ = 'joy'
import numpy as np
from scipy.sparse import csr_matrix, coo_matrix
import sys
# method for create csr
def save_sparse_csr(filename,array):
    np.savez(filename,data = array.data ,indices=array.indices,
             indptr =array.indptr, shape=array.shape )
# method for load matrix and change to csr
def convert(filename):
    # Create the appropriate format for the COO format.
    data = []
    row = []
    col = []
    f = open(filename)
    line = f.readline()
    row_num = 0
    while line:
        each_number_in_line_string = line.split(' ')
        if(each_number_in_line_string.__contains__('\n')):
            each_number_in_line_string.remove('\n')
        each_number_in_line_float =[]
        sum_of_squares = float(0)

        # get each number in line and there sum of squares
        for i in range(len(each_number_in_line_string)):
            number_float = float(each_number_in_line_string[i])
            each_number_in_line_float.append(number_float)
            if(number_float==0):
                continue
            sum_of_squares+=number_float*number_float

        # update and normalize each number in line
        if(sum_of_squares!= 0):
            for i in range(len(each_number_in_line_float)):
                if(each_number_in_line_float[i]==0):
                    continue
                each_number_in_line_float[i]= each_number_in_line_float[i]*10 / (sum_of_squares**0.5)

        # create coo's three line data
        for i in range(len(each_number_in_line_float)):
            col_num = i
            if(each_number_in_line_float[i]==0):
                continue
            data.append(each_number_in_line_float[col_num])
            row.append(row_num)
            col.append(col_num)

        row_num += 1
        line = f.readline()

    f.close()
    # read finished

    # Create the COO-matrix
    coo = coo_matrix((data,(row,col)))
    # Let Scipy convert COO to CSR format and return
    return csr_matrix(coo)
if __name__=='__main__':
    csr = convert(sys.argv[1])
    save_sparse_csr(sys.argv[2],csr)
