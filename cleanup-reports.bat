@echo off
REM Clean and Remove Test Artifacts
echo ============================================
echo BugBank - Clean Test Artifacts
echo ============================================
echo.
echo This will remove old test reports to make space
echo.
echo Removing target directory...
rmdir /s /q target
echo Target directory removed!
echo.
echo Removing test-output directory...
rmdir /s /q test-output
echo Test output directory removed!
echo.
echo ============================================
echo Cleanup Complete
echo ============================================
echo.
echo All old reports and artifacts have been deleted.
echo The next test run will generate fresh reports.
