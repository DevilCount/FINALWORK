from playwright.sync_api import sync_playwright
import time

def test_full_inspection_flow():
    with sync_playwright() as p:
        # 启动浏览器
        browser = p.chromium.launch(headless=False)
        page = browser.new_page()
        
        try:
            # 1. 访问登录页面
            print("访问登录页面...")
            page.goto('http://localhost:3000/')
            page.wait_for_load_state('networkidle')
            
            # 2. 登录系统
            print("登录系统...")
            page.fill('input[name="username"]', 'admin')
            page.fill('input[name="password"]', '123456')
            page.click('button[type="submit"]')
            page.wait_for_load_state('networkidle')
            
            # 3. 验证登录成功
            print("验证登录成功...")
            page.wait_for_selector('.el-menu-item', timeout=10000)
            
            # 4. 进入标本登记页面
            print("进入标本登记页面...")
            page.click('text=标本管理')
            page.click('text=标本登记')
            page.wait_for_load_state('networkidle')
            
            # 5. 登记新标本
            print("登记新标本...")
            # 填写标本信息
            page.fill('input[placeholder="患者ID"]', 'P99999')
            page.fill('input[placeholder="患者姓名"]', '测试患者')
            page.select_option('select[placeholder="性别"]', '男')
            page.fill('input[placeholder="年龄"]', '30')
            page.fill('input[placeholder="身份证号"]', '110101199601019999')
            page.fill('input[placeholder="联系电话"]', '13800138999')
            
            # 选择科室
            page.click('input[placeholder="科室"]')
            page.wait_for_selector('.el-select-dropdown')
            page.click('text=检验科')
            
            # 选择标本类型
            page.click('input[placeholder="标本类型"]')
            page.wait_for_selector('.el-select-dropdown')
            page.click('text=血液')
            
            # 选择检验项目
            page.click('input[placeholder="检验项目"]')
            page.wait_for_selector('.el-select-dropdown')
            page.click('text=血常规')
            page.click('text=肝功能')
            
            # 填写采集时间
            page.click('input[placeholder="采集时间"]')
            page.wait_for_selector('.el-date-picker')
            page.click('button.el-picker-panel__footer-btn.el-button--primary')
            
            # 提交标本
            page.click('button[type="submit"]')
            page.wait_for_load_state('networkidle')
            
            # 6. 进入标本接收页面
            print("进入标本接收页面...")
            page.click('text=标本接收')
            page.wait_for_load_state('networkidle')
            
            # 7. 接收标本
            print("接收标本...")
            # 输入条码（假设条码是BC开头的）
            page.fill('input[placeholder="请输入条码"]', 'BC')
            # 等待自动填充
            time.sleep(1)
            # 点击接收按钮
            page.click('text=接收')
            page.wait_for_load_state('networkidle')
            
            # 8. 进入结果录入页面
            print("进入结果录入页面...")
            page.click('text=报告管理')
            page.click('text=结果录入')
            page.wait_for_load_state('networkidle')
            
            # 9. 录入检验结果
            print("录入检验结果...")
            # 选择第一个标本
            page.click('tr.el-table__row >> nth=0')
            page.wait_for_load_state('networkidle')
            
            # 填写结果
            page.fill('input[placeholder="请输入结果"]', '7.5')
            page.click('text=保存')
            page.wait_for_load_state('networkidle')
            
            # 10. 提交报告
            print("提交报告...")
            page.click('text=提交')
            page.wait_for_load_state('networkidle')
            
            # 11. 进入报告审核页面
            print("进入报告审核页面...")
            page.click('text=报告审核')
            page.wait_for_load_state('networkidle')
            
            # 12. 审核报告
            print("审核报告...")
            # 选择第一个报告
            page.click('tr.el-table__row >> nth=0')
            page.wait_for_load_state('networkidle')
            
            # 点击审核通过
            page.click('text=审核通过')
            page.wait_for_load_state('networkidle')
            
            # 13. 进入报告发布页面
            print("进入报告发布页面...")
            page.click('text=报告发布')
            page.wait_for_load_state('networkidle')
            
            # 14. 发布报告
            print("发布报告...")
            # 选择第一个报告
            page.click('tr.el-table__row >> nth=0')
            page.wait_for_load_state('networkidle')
            
            # 点击发布
            page.click('text=发布')
            page.wait_for_load_state('networkidle')
            
            # 15. 测试完成
            print("测试完成！完整的检验流程已成功执行。")
            
        except Exception as e:
            print(f"测试过程中出现错误: {e}")
            # 截图保存错误状态
            page.screenshot(path='/tmp/test-error.png')
        finally:
            # 关闭浏览器
            browser.close()

if __name__ == "__main__":
    test_full_inspection_flow()