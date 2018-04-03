http://localhost:8080/SpringBootMybatisH2/

及び

http://localhost:8080/SpringBootMybatisH2/console

ユーザー名、パスワード共にsa

mybatis-generatorを使うときは、

generatorConfig.xmlにあるclassPathEntryを
OS環境に合わせて正しい値にすること　（ググれ）

また、webアプリを起動しておいてコマンドラインから
mvn mybatis-generator:generate
を実行すること


