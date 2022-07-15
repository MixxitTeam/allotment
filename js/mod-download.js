(function() {
  window.downloadMod = async function(button) {
    if (button)
      button.disabled = true;

    try {
      const projectInfoRes = await fetch("https://api.modrinth.com/v2/project/allotment");
      if (!projectInfoRes.ok)
        throw new Error("Failed to fetch project info");

      const projectInfo = await projectInfoRes.json();
      
      const versionInfoRes = await fetch(`https://api.modrinth.com/v2/version/${encodeURIComponent(projectInfo.versions[0])}`);
      if (!versionInfoRes.ok)
        throw new Error("Failed to fetch version info");

      const versionInfo = await versionInfoRes.json();

      location.href = versionInfo.files[0].url;
    } finally {
      if (button)
        button.disabled = false;
    }
  };
}())
