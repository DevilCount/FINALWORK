from playwright.sync_api import sync_playwright

def test_login():
    with sync_playwright() as p:
        # 启动浏览器
        browser = p.chromium.launch(headless=False)
        page = browser.new_page()
        
        try:
            # 1. 访问登录页面
            print("访问登录页面...")
            page.goto('http://localhost:3001/')
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
            print("登录成功！")
            
        except Exception as e:
            print(f"测试过程中出现错误: {e}")
            # 截图保存错误状态
            page.screenshot(path='/tmp/login-error.png')
        finally:
            # 关闭浏览器
            browser.close()

if __name__ == "__main__":
    test_login()