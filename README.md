CSRcluster
====
base on CSR matrix,archive K-means cluster
------
#the environment

    安装python需要用到的[依赖库](https://www.continuum.io/downloads "悬停显示") 根据系统下载对应的版本（推荐使用python2.7）
    install the dependent library of python ,download the edition decided by your system（recommend use python 2.7）

#how to use
1.import this whole project to eclipse <br>
2.open the integration.All.java       <br>
3.create a floder,and put DataSource.txt && k.txt in it <br>
4.run the three_in_one function to finish these steps below <br>

    first:calculate the TF-IDF matrix
    second:change the matrix to CSR matrix ,then run the cluster function,and find the best K
    third:divide the DataSource into several txts,and find everyone's keywords and hot questions
    this function take one parameter boolean 
    if true use Silhouette ways to find the best K  (datasource<=1W)
    if false use Inertia ways to find the best K   (datasource > 1W)

## after this, you should put a  Classname.txt into your floder,then run the last function
5.run the fourth function to finish the tag progress<br>

    fourth:tag everyline of DataSource with a main class name and a second class name

