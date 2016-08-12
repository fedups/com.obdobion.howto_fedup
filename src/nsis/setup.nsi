; Installer for howto_fedup

;======================================================
; Includes

  !include MUI.nsh
  !include Sections.nsh
  !include ..\..\target\project.nsh
  !include envvarupdate.nsh
  !include ReplaceInFile.nsh

;======================================================
; Installer Information

  Name "${PROJECT_NAME}"
  SetCompressor /SOLID lzma
  XPStyle on
  CRCCheck on
  InstallDir "C:\Program Files\Obdobion\howto\"
  AutoCloseWindow false
  ShowInstDetails show
  Icon "${NSISDIR}\Contrib\Graphics\Icons\orange-install.ico"

;======================================================
; Version Tab information for Setup.exe properties

  VIProductVersion ${PROJECT_VERSION}.0
  VIAddVersionKey ProductName "${PROJECT_NAME}.0"
  VIAddVersionKey ProductVersion "${PROJECT_VERSION}"
  VIAddVersionKey CompanyName "${PROJECT_ORGANIZATION_NAME}"
  VIAddVersionKey FileVersion "${PROJECT_VERSION}.0"
  VIAddVersionKey FileDescription ""
  VIAddVersionKey LegalCopyright ""

;======================================================
; Variables


;======================================================
; Modern Interface Configuration

  !define MUI_HEADERIMAGE
  !define MUI_ABORTWARNING
  !define MUI_COMPONENTSPAGE_SMALLDESC
  !define MUI_HEADERIMAGE_BITMAP_NOSTRETCH
  !define MUI_FINISHPAGE
  !define MUI_FINISHPAGE_TEXT "Thank you for installing the HowTo plugin - ${PROJECT_NAME}."
  !define MUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\orange-install.ico"

;======================================================
; Modern Interface Pages

  !define MUI_DIRECTORYPAGE_VERIFYONLEAVE
;  !insertmacro MUI_PAGE_LICENSE howto_license.txt
  !insertmacro MUI_PAGE_DIRECTORY
  !insertmacro MUI_PAGE_COMPONENTS
  !insertmacro MUI_PAGE_INSTFILES
  !insertmacro MUI_PAGE_FINISH

;======================================================
; Languages

  !insertmacro MUI_LANGUAGE "English"

;======================================================
; Installer Sections


Section "howto_fedup"

    SetOutPath $INSTDIR
    SetOverwrite on
    
    SetOutPath $INSTDIR\plugins\howto_fedup
    SetOverwrite on
    
    File ..\..\target\mavenDependenciesForNSIS\*.jar
    File /x *source* ..\..\target\howto_fedup-${PROJECT_VERSION}.jar
        
    ${EnvVarUpdate} $0 "PATH" "A" "HKLM" $INSTDIR
    
    createShortCut "$SMPROGRAMS\Obdobion\howto\HowTo fedup uninstall.lnk" "$INSTDIR\howto_fedup_uninstall.exe" "" ""
    
    writeUninstaller "$INSTDIR\howto_fedup_uninstall.exe"
SectionEnd

; Installer functions
Function .onInstSuccess

FunctionEnd

Section "uninstall"
    delete "$SMPROGRAMS\Obdobion\howto\HowTo fedup uninstall.lnk"
    delete "$INSTDIR\howto_fedup_uninstall.exe"
    rmdir /r $INSTDIR\plugins\howto_fedup
SectionEnd

Function .onInit
    InitPluginsDir
FunctionEnd