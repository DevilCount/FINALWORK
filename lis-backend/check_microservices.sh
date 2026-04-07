#!/bin/bash

echo "=========================================="
echo "后端微服务现状评估报告"
echo "=========================================="
echo ""

MICROSERVICES=("lis-common" "lis-gateway" "lis-auth" "lis-user" "lis-specimen" "lis-report" "lis-equipment" "lis-hl7" "lis-statistics" "lis-ai")

total_microservices=0
total_ok=0
total_warnings=0

for service in "${MICROSERVICES[@]}"; do
    echo "------------------------------------------"
    echo "检查微服务: $service"
    echo "------------------------------------------"
    ((total_microservices++))
    
    service_ok=true
    
    # 检查服务目录是否存在
    if [ -d "$service" ]; then
        echo "✓ 服务目录存在"
    else
        echo "✗ 服务目录不存在!"
        service_ok=false
        ((total_warnings++))
        echo ""
        continue
    fi
    
    # 检查pom.xml
    if [ -f "$service/pom.xml" ]; then
        echo "✓ pom.xml 存在"
    else
        echo "✗ pom.xml 不存在!"
        service_ok=false
        ((total_warnings++))
    fi
    
    # 检查src/main/java目录
    if [ -d "$service/src/main/java" ]; then
        echo "✓ src/main/java 目录存在"
        
        # 检查常见包结构
        for pkg in controller service mapper entity dto vo config; do
            pkg_path=$(find "$service/src/main/java" -type d -name "$pkg" 2>/dev/null | head -1)
            if [ -n "$pkg_path" ]; then
                count=$(find "$pkg_path" -type f -name "*.java" 2>/dev/null | wc -l)
                echo "  ✓ $pkg 包存在, 包含 $count 个Java文件"
            else
                echo "  - $pkg 包不存在 (此微服务可能不需要)"
            fi
        done
        
        # 统计Java文件总数
        java_files=$(find "$service/src/main/java" -name "*.java" 2>/dev/null | wc -l)
        echo "  总计: $java_files 个Java文件"
    else
        echo "✗ src/main/java 目录不存在!"
        service_ok=false
        ((total_warnings++))
    fi
    
    # 检查src/main/resources目录
    if [ -d "$service/src/main/resources" ]; then
        echo "✓ src/main/resources 目录存在"
        
        # 检查配置文件
        if [ -f "$service/src/main/resources/application.yml" ]; then
            echo "  ✓ application.yml 存在"
        elif [ -f "$service/src/main/resources/application.yaml" ]; then
            echo "  ✓ application.yaml 存在"
        else
            echo "  - application.yml/yaml 不存在"
        fi
        
        if [ -f "$service/src/main/resources/bootstrap.yml" ]; then
            echo "  ✓ bootstrap.yml 存在"
        elif [ -f "$service/src/main/resources/bootstrap.yaml" ]; then
            echo "  ✓ bootstrap.yaml 存在"
        fi
    else
        echo "✗ src/main/resources 目录不存在!"
        service_ok=false
        ((total_warnings++))
    fi
    
    if [ "$service_ok" = true ]; then
        ((total_ok++))
    fi
    
    echo ""
done

echo "=========================================="
echo "检查数据库脚本文件"
echo "=========================================="

SQL_FILES=("01_lis_user.sql" "02_lis_specimen.sql" "03_lis_report.sql" "04_lis_equipment.sql" "05_lis_hl7.sql" "06_lis_statistics.sql" "07_lis_ai.sql" "08_init_data.sql")

sql_ok=0
sql_total=0
if [ -d "sql" ]; then
    echo "✓ sql目录存在"
    for sql_file in "${SQL_FILES[@]}"; do
        ((sql_total++))
        if [ -f "sql/$sql_file" ]; then
            size=$(wc -c < "sql/$sql_file")
            lines=$(wc -l < "sql/$sql_file")
            echo "✓ $sql_file 存在 ($size 字节, $lines 行)"
            ((sql_ok++))
        else
            echo "✗ $sql_file 不存在!"
        fi
    done
else
    echo "✗ sql目录不存在!"
fi

echo ""
echo "=========================================="
echo "评估总结"
echo "=========================================="
echo "微服务总数: $total_microservices"
echo "检查通过: $total_ok"
echo "存在问题: $total_warnings"
echo "数据库脚本: $sql_ok/$sql_total"
echo "=========================================="
